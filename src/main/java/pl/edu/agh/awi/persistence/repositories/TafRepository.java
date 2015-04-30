package pl.edu.agh.awi.persistence.repositories;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import pl.edu.agh.awi.persistence.model.weather_condition.Taf;

import java.util.Collection;

public interface TafRepository extends GraphRepository<Taf> {

    @Query("MATCH (a:AirPort)-[:taf]->(t:Taf) " +
            "WHERE a.iataCode={0} " +
            "return t")
    Collection<Taf> findByAirportIata(String iata);
}
