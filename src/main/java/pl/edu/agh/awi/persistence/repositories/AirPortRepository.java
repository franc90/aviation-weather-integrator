package pl.edu.agh.awi.persistence.repositories;


import org.springframework.data.neo4j.annotation.Query;
import pl.edu.agh.awi.persistence.model.AirPort;

import java.util.Date;


public interface AirPortRepository extends AviationGraphRepository<AirPort> {

    @Override
    AirPort findByName(String name);

    @Override
    AirPort findByIcaoCode(String icaoCode);

    @Override
    AirPort findByIataCode(String iataCode);

    @Query("MATCH (airPort:AirPort {icaoCode : {0} } ) -[:metar]-> (metar:Metar) " +
            "WHERE metar.timestamp = {1} " +
            "RETURN count(metar) ")
    Integer countMetarsInAirPort(Date metarTimestamp, String icaoCode);

    @Query("MATCH (airPort:AirPort {icaoCode : {0} } ) -[:taf]-> (taf:Taf) " +
            "WHERE taf.validFrom = {1} AND taf.validTo = {2} " +
            "RETURN count(taf) ")
    Integer countTafsInAirPort(Date validFrom, Date validTo, String icaoCode);
}
