package pl.edu.agh.awi.persistence.repositories;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import pl.edu.agh.awi.persistence.model.Flight;

import java.util.Collection;
import java.util.Date;

public interface FlightRepository extends GraphRepository<Flight> {

    Flight findByFlightId(Long flightId);

    Collection<Flight> findByAirLineIcaoCode(String icaoCode);

    Collection<Flight> findByAirLineIataCode(String iataCode);

    Collection<Flight> findByDepartureAirportIcaoCode(String icaoCode);

    Collection<Flight> findByDepartureAirportIataCode(String iataCode);

    @Query("MATCH (flight:Flight) "+
            "WHERE flight.scheduledDepartureTime >= {0} AND flight.scheduledDepartureTime < {1} " +
            "RETURN flight ")
    Collection<Flight> findByScheduledDepartureTimeBetween(long timeFromStartDate, long timeFromEndDate);

    @Query("MATCH (flight:Flight) "+
            "WHERE flight.scheduledArrivalTime >= {0} AND flight.scheduledArrivalTime < {1} " +
            "RETURN flight ")
    Collection<Flight> findByScheduledArrivalTimeBetween(long timeFromStartDate, long timeFromEndDate);
}
