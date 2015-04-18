package pl.edu.agh.awi.persistence.repositories;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import pl.edu.agh.awi.persistence.model.Zone;

public interface ZoneRepository extends GraphRepository<Zone> {

    @Query("MATCH (zone:Zone) " +
            "WITH zone, (abs(zone.maximalLatitude - zone.minimalLatitude)) as maxLatitude, " +
            "           (abs(zone.maximalLongitude - zone.minimalLongitude)) as maxLongitude " +
            "ORDER BY maxLatitude, maxLongitude DESC LIMIT 1 " +
            "RETURN zone ")
    Zone findWidestZone();
}
