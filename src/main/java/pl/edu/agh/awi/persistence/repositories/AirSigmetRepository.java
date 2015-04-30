package pl.edu.agh.awi.persistence.repositories;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import pl.edu.agh.awi.persistence.model.weather_condition.AirSigmet;

import java.util.Collection;

public interface AirSigmetRepository extends GraphRepository<AirSigmet> {

    @Query("MATCH (a:AirPort)-[:airsigmet]->(as:AirSigmet) " +
            "WHERE a.iataCode={0} " +
            "RETURN as")
    Collection<AirSigmet> findByAirportIata(String iata);

}
