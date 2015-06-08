package pl.edu.agh.awi.persistence.repositories;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import pl.edu.agh.awi.persistence.model.weather_condition.Metar;

import java.util.Collection;

public interface MetarRepository extends GraphRepository<Metar> {

    @Query("MATCH (a:AirPort)-[:metar]->(m:Metar) " +
            "WHERE a.iataCode={0} " +
            "RETURN m")
    Collection<Metar> findByAirportIata(String iata);

}
