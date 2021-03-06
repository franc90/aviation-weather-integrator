package pl.edu.agh.awi.persistence.repositories;

import org.springframework.data.neo4j.annotation.Query;
import pl.edu.agh.awi.persistence.model.Zone;

public interface ZoneRepository extends OptionalSaveGraphRepository<Zone> {

    @Query("MATCH (zone:Zone) " +
            "WITH zone, ( abs(zone.maximalLatitude - zone.minimalLatitude) + abs(zone.maximalLongitude - zone.minimalLongitude) ) as maxSum " +
            "ORDER BY maxSum DESC LIMIT 1 " +
            "RETURN zone ")
    Zone findWidestZone();

}
