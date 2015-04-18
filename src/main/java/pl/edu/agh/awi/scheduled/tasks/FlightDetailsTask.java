package pl.edu.agh.awi.scheduled.tasks;

import com.hazelcast.core.IMap;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import pl.edu.agh.awi.downloader.exceptions.FlightTaskException;
import pl.edu.agh.awi.downloader.flights.flightDetails.client.FlightDetailsClient;
import pl.edu.agh.awi.downloader.flights.flightDetails.data.FlightDetailsResponse;
import pl.edu.agh.awi.persistence.PersistenceService;
import pl.edu.agh.awi.persistence.model.Flight;
import pl.edu.agh.awi.persistence.model.LoadBalancer;
import pl.edu.agh.awi.persistence.repositories.FlightRepository;
import pl.edu.agh.awi.persistence.repositories.LoadBalancerRepository;
import pl.edu.agh.awi.scheduled.CronHelper;
import pl.edu.agh.awi.scheduled.cache.CachedFlight;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
@Transactional
public class FlightDetailsTask extends AbstractHazelcastTask {

    private final Logger logger = Logger.getLogger("FlightDetailsTask");

    private IMap<String, CachedFlight> flights;

    private IMap<String, CachedFlight> finishedFlights;

    @Autowired
    private FlightDetailsClient client;

    @Autowired
    private PersistenceService persistenceService;

    @Override
    public void init() {
        flights = hazelcast.getMap("flights");
        finishedFlights = hazelcast.getMap("finishedFlights");
    }

    @Scheduled(cron = CronHelper.QUATERLY_CRON)
    public void task() {
        LoadBalancer loadBalancer;
        try {
            loadBalancer = loadBalancer();
        } catch (FlightTaskException ex) {
            return;
        }

        if (flights.isEmpty()) {
            downloadUsingDB(loadBalancer);
        } else {
            downloadUsingCache(loadBalancer);
        }
    }

    private LoadBalancer loadBalancer() {
        Collection<LoadBalancer> loadBalancers = persistenceService.findAllLoadBalancers();

        if (CollectionUtils.isEmpty(loadBalancers)) {
            throw new FlightTaskException("No loadBalancer");
        }

        return loadBalancers
                .stream()
                .min((x, y) -> x.getLoad().compareTo(y.getLoad()))
                .get();
    }

    private void downloadUsingDB(LoadBalancer loadBalancer) {
        Collection<Flight> dbflights = persistenceService.findNotLandedFlights();

        List<Flight> purgedFlights = removeOld(dbflights);
        purgedFlights.forEach(f -> download(f, loadBalancer));
    }

    private List<Flight> removeOld(Collection<Flight> dbflights) {
        return dbflights
                .stream()
                .filter(f -> isRecentFlight(f))
                .collect(Collectors.toList());
    }

    private boolean isRecentFlight(Flight flight) {
        return flight.getScheduledDepartureTime() == null ||
                Duration
                        .between(
                                LocalDateTime
                                        .now()
                                        .minusHours(36),
                                flight
                                        .getScheduledDepartureTime()
                                        .toInstant()
                        ).minusHours(36)
                        .isNegative();
    }

    private void downloadUsingCache(LoadBalancer loadBalancer) {
        flights
                .keySet()
                .forEach(flightId -> {
                    Flight flight = persistenceService.findFlightByFlightId(flightId);
                    if (flight != null) {
                        download(flight, loadBalancer);
                    }
                });
    }

    private void download(Flight flight, LoadBalancer loadBalancer) {
        logger.info("Downloading details for " + flight.getFlightId());

        FlightDetailsResponse response = client
                .withLoadBalancer(loadBalancer.getDomain())
                .withFlightId(flight.getFlightId())
                .getResponse();

        if (response == null) {
            return;
        }

        logger.info("Updating flight " + flight.getFlightId());
        updateFlight(flight, response);
        persistenceService.saveFlight(flight);

        if ("landed".equals(flight.getStatus())) {
            updateCaches(flight.getFlightId());
        }
    }

    private void updateFlight(Flight flight, FlightDetailsResponse response) {
        flight.setActualArrivalTime(ObjectUtils.defaultIfNull(response.getAta(), flight.getActualArrivalTime()));
        flight.setActualDepartureTime(ObjectUtils.defaultIfNull(response.getAtd(), flight.getActualDepartureTime()));
        flight.setScheduledArrivalTime(ObjectUtils.defaultIfNull(response.getSta(), flight.getScheduledArrivalTime()));
        flight.setScheduledDepartureTime(ObjectUtils.defaultIfNull(response.getStd(), flight.getScheduledDepartureTime()));
        flight.setStatus(ObjectUtils.defaultIfNull(response.getStatus(), flight.getStatus()));
    }

    private void updateCaches(String flightId) {
        CachedFlight removed = flights.remove(flightId);
        finishedFlights.put(flightId, removed);
    }

}
