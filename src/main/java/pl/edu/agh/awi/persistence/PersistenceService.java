package pl.edu.agh.awi.persistence;

import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.awi.persistence.model.*;
import pl.edu.agh.awi.persistence.model.weather_condition.AirSigmet;
import pl.edu.agh.awi.persistence.model.weather_condition.Metar;
import pl.edu.agh.awi.persistence.model.weather_condition.Taf;

import java.util.Collection;

public interface PersistenceService {
    @Transactional(readOnly = true)
    Collection<Zone> findAllZones();

    @Transactional(readOnly = true)
    Collection<LoadBalancer> findAllLoadBalancers();

    @Transactional
    void saveFlight(Flight flight);

    @Transactional(readOnly = true)
    Flight findFlightByFlightId(String flightId);

    @Transactional(readOnly = true)
    Collection<Flight> findNotLandedFlights();

    @Transactional(readOnly = true)
    Collection<AirPort> findAllAirPorts();

    @Transactional
    Collection<Flight> findFlightsByAirLineIataCode(String iata);

    @Transactional
    Collection<Flight> findFlightByDepartureAirportIataCode(String iata);

    @Transactional
    Collection<Flight> findFlightByArrivalAirportIataCode(String iata);

    @Transactional
    void saveAirport(AirPort airPort);

    @Transactional
    AirLine findAirLineByIataCode(String iata);

    @Transactional
    AirLine findAirLineByIcaoCode(String icao);

    @Transactional
    AirPort findAirPortByIataCode(String iata);

    @Transactional(readOnly = true)
    boolean isMetarNotConnectedWithAirPort(Metar metar, AirPort airPort);

    @Transactional(readOnly = true)
    boolean isTafNotConnectedWithAirPort(Taf taf, AirPort airPort);

    @Transactional
    void addMetar(AirPort airPort, Metar metar);

    @Transactional
    void addTaf(AirPort airPort, Taf taf);

    @Transactional
    void addAirsigmet(AirPort airport, AirSigmet airSigmet);
}
