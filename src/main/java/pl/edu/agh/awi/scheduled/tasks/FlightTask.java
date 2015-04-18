package pl.edu.agh.awi.scheduled.tasks;

import com.hazelcast.core.IList;
import com.hazelcast.core.IMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import pl.edu.agh.awi.downloader.exceptions.FlightTaskException;
import pl.edu.agh.awi.downloader.flights.flight.client.FlightClient;
import pl.edu.agh.awi.downloader.flights.flight.data.Flight;
import pl.edu.agh.awi.downloader.flights.flight.data.FlightResponse;
import pl.edu.agh.awi.persistence.model.LoadBalancer;
import pl.edu.agh.awi.persistence.model.Zone;
import pl.edu.agh.awi.persistence.repositories.FlightRepository;
import pl.edu.agh.awi.persistence.repositories.LoadBalancerRepository;
import pl.edu.agh.awi.persistence.repositories.ZoneRepository;
import pl.edu.agh.awi.scheduled.CronHelper;
import pl.edu.agh.awi.scheduled.cache.CachedFlight;
import pl.edu.agh.awi.scheduled.cache.CachedFlightBuilder;
import pl.edu.agh.awi.scheduled.converter.FlightPersistenceConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Component
@Transactional
public class FlightTask extends AbstractHazelcastTask {

    private static final String NORTHAMERICA = "northamerica";

    private final Logger logger = Logger.getLogger("FlightTask");

    private IMap<String, CachedFlight> flights;

    private IMap<String, CachedFlight> finishedFlights;

    private IList<Zone> zones;

    @Autowired
    private FlightClient client;

    @Autowired
    private FlightPersistenceConverter flightPersistenceConverter;

    @Autowired
    private LoadBalancerRepository loadBalancerRepository;

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Override
    public void init() {
        flights = hazelcast.getMap("flights");
        finishedFlights = hazelcast.getMap("finishedFlights");
        zones = hazelcast.getList("zones");
    }

    @Scheduled(cron = CronHelper.QUATERLY_CRON)
    public void task() {
        Zone zone;
        LoadBalancer loadBalancer;
        try {
            zone = zone();
            loadBalancer = loadBalancer();
        } catch (FlightTaskException ex) {
            return;
        }

        downloadFlights(zone, loadBalancer);
    }

    private Zone zone() {
        if (CollectionUtils.isEmpty(zones)) {
            return getZone(loadZoneFromDb(), NORTHAMERICA);
        }

        return getZone(zones, NORTHAMERICA);
    }

    private Zone getZone(List<Zone> zones, String zoneName) {
        for (Zone zone : zones) {
            if (zoneName.equals(zone.getName())) {
                return zone;
            }
        }

        throw new FlightTaskException("No zone " + zoneName);
    }

    private List<Zone> loadZoneFromDb() {
        Collection<Zone> zones = zoneRepository.findAll().as(Collection.class);

        if (CollectionUtils.isEmpty(zones)) {
            throw new FlightTaskException("No zone");
        }

        return new ArrayList<>(zones);
    }

    private LoadBalancer loadBalancer() {
        Collection<LoadBalancer> loadBalancers = loadBalancerRepository.findAll().as(Collection.class);

        if (CollectionUtils.isEmpty(loadBalancers)) {
            throw new FlightTaskException("No loadBalancer");
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

    private boolean doesNotContain(IMap<String, CachedFlight> flights, Flight flight) {
        CachedFlight cachedFlight = flights.get(flight.getFlightId());
        return cachedFlight == null;
    }

    private void saveFlight(Flight flight) {
        try {
            pl.edu.agh.awi.persistence.model.Flight persistenceFlight = flightPersistenceConverter.convert(flight);

            logger.info("Processing " + persistenceFlight.getAirLine().getIataCode() + "-" + persistenceFlight.getFlightId() + "-" + persistenceFlight.getDepartureAirport().getCity());
            flightRepository.save(persistenceFlight);

            CachedFlight cachedFlight = convert(flight);
            flights.put(flight.getFlightId(), cachedFlight);
        } catch (FlightTaskException e) {
            logger.info(e.getMessage());
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
