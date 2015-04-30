package pl.edu.agh.awi.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.awi.persistence.model.*;
import pl.edu.agh.awi.persistence.model.weather_condition.AirSigmet;
import pl.edu.agh.awi.persistence.model.weather_condition.Metar;
import pl.edu.agh.awi.persistence.model.weather_condition.Taf;
import pl.edu.agh.awi.persistence.repositories.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

@Component
public class PersistenceServiceImpl implements PersistenceService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private LoadBalancerRepository loadBalancerRepository;

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private AirPortRepository airPortRepository;

    @Autowired
    private AirLineRepository airLineRepository;

    @Override
    @Transactional(readOnly = true)
    public Collection<Zone> findAllZones() {
        return zoneRepository.findAll().as(Collection.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<LoadBalancer> findAllLoadBalancers() {
        return loadBalancerRepository.findAll().as(Collection.class);
    }

    @Override
    @Transactional
    public void saveFlight(Flight flight) {
        flightRepository.saveOnly(flight);
    }

    @Override
    @Transactional(readOnly = true)
    public Flight findFlightByFlightId(String flightId) {
        return extractFirstElement(flightRepository.findByFlightId(flightId));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Flight> findNotLandedFlights() {
        return flightRepository.findNotLanded();
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<AirPort> findAllAirPorts() {
        return airPortRepository.findAll().as(Collection.class);
    }

    @Override
    @Transactional
    public Collection<Flight> findFlightsByAirLineIataCode(String iata) {
        return flightRepository.findByAirLineIataCode(iata);
    }

    @Override
    @Transactional
    public Collection<Flight> findFlightByDepartureAirportIataCode(String iata) {
        return flightRepository.findByDepartureAirportIcaoCode(iata);
    }

    @Override
    @Transactional
    public Collection<Flight> findFlightByArrivalAirportIataCode(String iata) {
        return flightRepository.findByArrivalAirportIataCode(iata);
    }

    @Override
    @Transactional
    public void saveAirport(AirPort airPort) {
        airPortRepository.saveOnly(airPort);
    }

    @Override
    @Transactional
    public AirLine findAirLineByIataCode(String iata) {
        return extractFirstElement(airLineRepository.findByIataCode(iata));
    }

    @Override
    @Transactional
    public AirLine findAirLineByIcaoCode(String icao) {
        return extractFirstElement(airLineRepository.findByIcaoCode(icao));
    }

    @Override
    @Transactional
    public AirPort findAirPortByIataCode(String iata) {
        return extractFirstElement(airPortRepository.findByIataCode(iata));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isMetarNotConnectedWithAirPort(Metar metar, AirPort airPort) {
        return airPortRepository.countMetarsInAirPort(metar.getTimestamp(), airPort.getIcaoCode()) == 0;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isTafNotConnectedWithAirPort(Taf taf, AirPort airPort) {
        return airPortRepository.countTafsInAirPort(taf.getValidFrom(), taf.getValidTo(), airPort.getIcaoCode()) == 0;
    }

    @Override
    @Transactional
    public void addMetar(AirPort airPort, Metar metar) {
        airPort.addMetar(metar);
    }

    @Override
    @Transactional
    public void addTaf(AirPort airPort, Taf taf) {
        airPort.addTaf(taf);
    }

    @Override
    @Transactional
    public void addAirsigmet(AirPort airport, AirSigmet airSigmet) {
        airport.addAirSigmet(airSigmet);
    }

    public <T> T extractFirstElement(Iterable<T> iterable) {
        return Stream.of(iterable)
                .map(Iterable::iterator)
                .filter(Iterator::hasNext)
                .map(Iterator::next)
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(this::createException);
    }

    private PersistenceException createException() {
        return new PersistenceException("Cannot find node") ;
    }
}
