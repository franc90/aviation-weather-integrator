package pl.edu.agh.awi.scheduler.tasks;

import com.google.common.cache.Cache;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import pl.edu.agh.awi.downloader.exceptions.MalformedUrlException;
import pl.edu.agh.awi.downloader.flights.flightDetails.client.FlightDetailsClient;
import pl.edu.agh.awi.downloader.flights.flightDetails.data.FlightDetailsResponse;
import pl.edu.agh.awi.persistence.PersistenceService;
import pl.edu.agh.awi.persistence.model.Flight;
import pl.edu.agh.awi.persistence.model.LoadBalancer;
import pl.edu.agh.awi.scheduler.cache.CachedFlight;
import pl.edu.agh.awi.scheduler.exception.SchedulerException;
import pl.edu.agh.awi.scheduler.helper.CronHelper;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class FlightDetailsTask {

    private final Logger logger = Logger.getLogger("FlightDetailsTask");

    @Resource
    private Cache<String, CachedFlight> flights;

    @Resource
    private Cache<String, CachedFlight> finishedFlights;

    @Autowired
    private FlightDetailsClient client;

    @Autowired
    private PersistenceService persistenceService;

    @Scheduled(cron = CronHelper.FLIGHT_DETAIL_CRON)
    public void task() {
        LoadBalancer loadBalancer;
        try {
            loadBalancer = loadBalancer();
        } catch (SchedulerException ex) {
            return;
        }

        try {
            download(loadBalancer);
        } catch (MalformedUrlException ex) {
            String flightKey = ex.getParameter();

            logger.info("Could not load flights for " + flightKey);

            CachedFlight removedFlight = flights.getIfPresent(flightKey);
            if (removedFlight != null) {
                flights.invalidate(flightKey);
                finishedFlights.put(flightKey, removedFlight);
            }
        }
    }

    private LoadBalancer loadBalancer() {
        Collection<LoadBalancer> loadBalancers = persistenceService.findAllLoadBalancers();

        if (CollectionUtils.isEmpty(loadBalancers)) {
            throw new SchedulerException("No loadBalancer");
        }

        return loadBalancers
                .stream()
                .min((x, y) -> x.getLoad().compareTo(y.getLoad()))
                .get();
    }

    private void download(LoadBalancer loadBalancer) {
        if (flights.size() == 0) {
            downloadUsingDB(loadBalancer);
        } else {
            downloadUsingCache(loadBalancer);
        }
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
                                LocalDateTime
                                        .ofInstant(
                                                Instant.ofEpochMilli(flight
                                                        .getScheduledDepartureTime()
                                                        .getTime()),
                                                ZoneId.of("UTC")
                                        )
                        ).minusHours(36)
                        .isNegative();
    }

    private void downloadUsingCache(LoadBalancer loadBalancer) {
        flights
                .asMap()
                .keySet()
                .forEach(flightId -> {
                    Flight flight = persistenceService.findFlightByFlightId(flightId);
                    if (flight != null) {
                        download(flight, loadBalancer);
                    }
                });
    }

    private void download(Flight flight, LoadBalancer loadBalancer) {

        FlightDetailsResponse response = client
                .withLoadBalancer(loadBalancer.getDomain())
                .withFlightId(flight.getFlightId())
                .getResponse();

        if (response == null) {
            logger.info("No response for " + flight.getFlightId());
            return;
        }


        updateFlight(flight, response);
        persistenceService.saveFlight(flight);

        logger.info("Flight downloaded " + flight.getFlightId());
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
        CachedFlight removed = flights.getIfPresent(flightId);
        if (removed != null) {
            flights.invalidate(flightId);
            finishedFlights.put(flightId, removed);
        }
    }

}
