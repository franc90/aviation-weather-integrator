package pl.edu.agh.awi.scheduled.tasks;

import com.hazelcast.core.IList;
import com.hazelcast.core.IMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
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

import java.util.Date;
import java.util.List;

@Component
public class FlightTask extends AbstractHazelcastTask {

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

    @Scheduled(cron = CronHelper.EVERY_QUARTER)
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
            return loadZoneFromDb();
        }

        return zones.get(0);
    }

    private Zone loadZoneFromDb() {
        Result<Zone> all = zoneRepository.findAll();
        Zone zone = all.singleOrNull();

        if (zone == null) {
            throw new FlightTaskException("No zone");
        }

        return zone;
    }

    private LoadBalancer loadBalancer() {
        Result<LoadBalancer> all = loadBalancerRepository.findAll();

        LoadBalancer loadBalancer = all.singleOrNull();
        if (loadBalancer == null) {
            throw new FlightTaskException("No loadBalancer");
        }

        for (LoadBalancer balancer : all) {
            if (balancer.getLoad() < loadBalancer.getLoad()) {
                loadBalancer = balancer;
            }
        }
        return loadBalancer;
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
        pl.edu.agh.awi.persistence.model.Flight persistenceFlight = flightPersistenceConverter.convert(flight);
        flightRepository.save(persistenceFlight);

        CachedFlight cachedFlight = convert(flight);
        flights.put(flight.getFlightId(), cachedFlight);
    }

    private CachedFlight convert(Flight flight) {
        return new CachedFlightBuilder()
                .setArrivalIata(flight.getArrivalIata())
                .setDepartureIata(flight.getDepartureIata())
                .setLastUpdated(new Date())
                .build();
    }

}
