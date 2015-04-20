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

    @Query("MATCH (airPort:AirPort {icaoCode : {1} } ) -[:metar]-> (metar:Metar) " +
            "WHERE metar.timestamp = {0} " +
            "RETURN count(metar) ")
    Integer countMetarsInAirPort(Date metarTimestamp, String icaoCode);

    @Query("MATCH (airPort:AirPort {icaoCode : {2} } ) -[:taf]-> (taf:Taf) " +
            "WHERE taf.validFrom = {0} AND taf.validTo = {1} " +
            "RETURN count(taf) ")
    Integer countTafsInAirPort(Date validFrom, Date validTo, String icaoCode);
}
