package pl.edu.agh.awi.persistence.repositories;


import org.springframework.data.neo4j.repository.GraphRepository;
import pl.edu.agh.awi.persistence.model.AirPort;

public interface AirPortRepository extends GraphRepository<AirPort> {
}
