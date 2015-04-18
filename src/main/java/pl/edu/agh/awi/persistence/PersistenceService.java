package pl.edu.agh.awi.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.awi.persistence.model.Flight;
import pl.edu.agh.awi.persistence.model.LoadBalancer;
import pl.edu.agh.awi.persistence.model.Zone;
import pl.edu.agh.awi.persistence.repositories.FlightRepository;
import pl.edu.agh.awi.persistence.repositories.LoadBalancerRepository;
import pl.edu.agh.awi.persistence.repositories.ZoneRepository;

import java.util.Collection;

@Component
public class PersistenceService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private LoadBalancerRepository loadBalancerRepository;

    @Autowired
    private ZoneRepository zoneRepository;

    @Transactional(readOnly = true)
    public Collection<Zone> findAllZones() {
        return zoneRepository.findAll().as(Collection.class);
    }

    @Transactional(readOnly = true)
    public Collection<LoadBalancer> findAllLoadBalancers() {
        return loadBalancerRepository.findAll().as(Collection.class);
    }

    @Transactional
    public void saveFlight(Flight flight) {
        flightRepository.saveOnly(flight);
    }

    @Transactional(readOnly = true)
    public Flight findFlightByFlightId(String flightId) {
        return flightRepository.findByFlightId(flightId);
    }

    @Transactional(readOnly = true)
    public Collection<Flight> findNotLandedFlights() {
        return flightRepository.findNotLanded();
    }

}
