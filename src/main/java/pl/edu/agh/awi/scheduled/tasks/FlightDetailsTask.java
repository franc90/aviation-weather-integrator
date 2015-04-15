package pl.edu.agh.awi.scheduled.tasks;

import com.hazelcast.core.IMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.edu.agh.awi.downloader.exceptions.FlightTaskException;
import pl.edu.agh.awi.downloader.flights.flightDetails.client.FlightDetailsClient;
import pl.edu.agh.awi.downloader.flights.flightDetails.data.FlightDetailsResponse;
import pl.edu.agh.awi.persistence.model.Flight;
import pl.edu.agh.awi.persistence.model.FlightDetail;
import pl.edu.agh.awi.persistence.model.LoadBalancer;
import pl.edu.agh.awi.persistence.repositories.FlightRepository;
import pl.edu.agh.awi.persistence.repositories.LoadBalancerRepository;
import pl.edu.agh.awi.scheduled.CronHelper;
import pl.edu.agh.awi.scheduled.cache.CachedFlight;
import pl.edu.agh.awi.scheduled.converter.FlightDetailConverter;

import java.util.List;

@Component
public class FlightDetailsTask extends AbstractHazelcastTask {

    private IMap<String, CachedFlight> flights;

    private IMap<String, CachedFlight> finishedFlights;

    @Autowired
    private FlightDetailsClient client;

    @Autowired
    private LoadBalancerRepository loadBalancerRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Override
    public void init() {
        flights = hazelcast.getMap("flights");
        finishedFlights = hazelcast.getMap("finishedFlights");
    }

    @Scheduled(cron = CronHelper.EVERY_QUARTER)
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

    private void downloadUsingDB(LoadBalancer loadBalancer) {
//        List<Flight> dbFlights = flightRepository.f;
         // TODO load form db and remove finished or old
        // TODO add method to download only not landed flights

//        dbFlights.forEach(f -> download(f, loadBalancer));
    }

    private void downloadUsingCache(LoadBalancer loadBalancer) {
        flights
                .keySet()
                .forEach(flightId -> {
                    Flight flight = flightRepository.findByFlightId(flightId);
                    download(flight, loadBalancer);
                });
    }

    private void download(Flight flight, LoadBalancer loadBalancer) {
        FlightDetailsResponse response = client
                .withLoadBalancer(loadBalancer.getDomain())
                .withFlightId(flight.getFlightId())
                .getResponse();

        if (response == null) {
            return;
        }

        updateFlight(flight, response);

        FlightDetail details = FlightDetailConverter.convert(response);
        flight.addFlightDetail(details);
        flightRepository.save(flight);

        if ("landed".equals(flight.getStatus())) {
            updateCaches(flight.getFlightId());
        }
    }

    private void updateFlight(Flight flight, FlightDetailsResponse response) {
        flight.setActualArrivalTime(response.getAta());
        flight.setActualDepartureTime(response.getAtd());
        flight.setScheduledArrivalTime(response.getSta());
        flight.setScheduledDepartureTime(response.getStd());
        flight.setStatus(response.getStatus());
    }

    private void updateCaches(String flightId) {
        CachedFlight removed = flights.remove(flightId);
        finishedFlights.put(flightId, removed);
    }

}
