package pl.edu.agh.awi.scheduler.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import pl.edu.agh.awi.downloader.exceptions.MalformedUrlException;
import pl.edu.agh.awi.downloader.flights.flight.client.FlightClient;
import pl.edu.agh.awi.downloader.flights.flight.data.Flight;
import pl.edu.agh.awi.downloader.flights.flight.data.FlightResponse;
import pl.edu.agh.awi.persistence.PersistenceException;
import pl.edu.agh.awi.persistence.PersistenceService;
import pl.edu.agh.awi.persistence.model.LoadBalancer;
import pl.edu.agh.awi.persistence.model.Zone;
import pl.edu.agh.awi.scheduler.cache.CachedFlight;
import pl.edu.agh.awi.scheduler.cache.CachedFlightBuilder;
import pl.edu.agh.awi.scheduler.converter.FlightPersistenceConverter;
import pl.edu.agh.awi.scheduler.exception.SchedulerException;
import pl.edu.agh.awi.scheduler.helper.CronHelper;
import pl.edu.agh.awi.scheduler.helper.ZoneHelper;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class FlightTask {

    private static final String NORTH_AMERICA = "northamerica";

    private final Logger logger = Logger.getLogger("FlightTask");

    @Resource
    private Map<String, CachedFlight> flights;

    @Resource
    private Map<String, CachedFlight> finishedFlights;

    @Autowired
    private FlightClient client;

    @Autowired
    private FlightPersistenceConverter flightPersistenceConverter;

    @Autowired
    private PersistenceService persistenceService;

    @Autowired
    private ZoneHelper zoneHelper;

    @Scheduled(cron = CronHelper.FLIGHT_CRON)
    public void task() {
        Zone zone;
        LoadBalancer loadBalancer;
        try {
            zone = zoneHelper.loadZone(NORTH_AMERICA);
            loadBalancer = loadBalancer();
        } catch (SchedulerException ex) {
            logger.info(ex.getMessage());
            return;
        }

        try {
            downloadFlights(zone, loadBalancer);
        } catch (MalformedUrlException ex) {
            String flightKey = ex.getParameter();

            logger.info("Could not load flights for " + flightKey);

            CachedFlight removedFlight = flights.remove(flightKey);
            finishedFlights.put(flightKey, removedFlight);
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

    private void downloadFlights(Zone zone, LoadBalancer loadBalancer) {
        FlightResponse response = client
                .withZone(zone.getName())
                .withLoadBalancer(loadBalancer.getDomain())
                .getResponse();

        if (response != null && !CollectionUtils.isEmpty(response.getFlights())) {
            save(response.getFlights());
        }

        zone.getSubzones().forEach(e -> downloadFlights(e, loadBalancer));
    }

    private void save(List<Flight> downloadedFlights) {
        downloadedFlights
                .stream()
                .filter(e -> doesNotContain(flights, e))
                .filter(e -> doesNotContain(finishedFlights, e))
                .forEach(this::saveFlight);
    }

    private boolean doesNotContain(Map<String, CachedFlight> flights, Flight flight) {
        CachedFlight cachedFlight = flights.get(flight.getFlightId());
        return cachedFlight == null;
    }

    private void saveFlight(Flight flight) {
        try {
            pl.edu.agh.awi.persistence.model.Flight persistenceFlight = flightPersistenceConverter.convert(flight);

            logger.info("Processing " + persistenceFlight.getAirLine().getIataCode() + "-" + persistenceFlight.getFlightId() + "-" + persistenceFlight.getDepartureAirport().getCity());
            persistenceService.saveFlight(persistenceFlight);

            CachedFlight cachedFlight = convert(flight);
            flights.put(flight.getFlightId(), cachedFlight);
        } catch (SchedulerException e) {
//            Not logging to many dest/arr airports get not processed.
//            logger.info(e.getMessage());
        } catch (PersistenceException e) {
            logger.fine(e.getMessage() + " for " + flight);
        }
    }

    private CachedFlight convert(Flight flight) {
        return new CachedFlightBuilder()
                .setArrivalIata(flight.getArrivalIata())
                .setDepartureIata(flight.getDepartureIata())
                .setLastUpdated(new Date())
                .build();
    }

}
