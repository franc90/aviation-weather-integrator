package pl.edu.agh.awi.persistence.repositories;

import org.springframework.data.neo4j.repository.GraphRepository;
import pl.edu.agh.awi.persistence.model.Flight;

public interface FlightRepository extends GraphRepository<Flight> {

    Flight findByFlightId(Long flightId);
}
