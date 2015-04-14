package pl.edu.agh.awi.persistence.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.awi.persistence.PersistenceConfig;
import pl.edu.agh.awi.persistence.TestDatabaseConfig;
import pl.edu.agh.awi.persistence.model.*;

import java.util.Date;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration(classes = {PersistenceConfig.class, TestDatabaseConfig.class})
@ActiveProfiles("test")
@Transactional
public class FlightRepositoryTest {

    private static final String DEPARTURE_NAME = "departure";
    private static final String ARRIVAL_NAME = "arrival";
    private static final String AIR_LINE = "airline";
    private static final Long EXISTING_FLIGHT_ID = 10L;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private Neo4jTemplate neo4jTemplate;

    private AirPort departureAirPort;
    private AirPort arrivalAirPort;
    private AirLine airLine;
    private Flight existingFlight;
    private AirPort redirectedAirPort;

    @Before
    public void feedDatabase() {
        departureAirPort = saveToDatabase(createAirPortWithName(DEPARTURE_NAME));
        arrivalAirPort = saveToDatabase(createAirPortWithName(ARRIVAL_NAME));
        redirectedAirPort = saveToDatabase(createAirPortWithName("some_redirected_airport"));
        airLine = saveToDatabase(createAirLineWithName(AIR_LINE));
        existingFlight = saveToDatabase(createFlight());
    }

    private <T> T saveToDatabase(T item) {
        return neo4jTemplate.save(item);
    }

    private AirPort createAirPortWithName(String name) {
        AirPort airPort = new AirPort();
        airPort.setName(name);
        return airPort;
    }

    private AirLine createAirLineWithName(String name) {
        AirLine airLine = new AirLine();
        airLine.setName(name);
        return airLine;
    }

    private Flight createFlight() {
        return ModelBuilder.build(Flight::new, f -> {
            f.setFlightId(EXISTING_FLIGHT_ID);
            f.setStatus("ON_AIR");
            f.setDepartureAirport(departureAirPort);
            f.addDestinationAirPort(arrivalAirPort);
        });
    }

    @Test
    public void shouldFindFlightById() {
        Flight flight = flightRepository.findByFlightId(EXISTING_FLIGHT_ID);
        assertEquals(existingFlight, flight);
    }

    @Test
    public void shouldUpdateFlightStatus() {
        Flight flight = flightRepository.findByFlightId(EXISTING_FLIGHT_ID);
        String status = "LANDED";
        flight.setStatus(status);
        flightRepository.saveOnly(flight);
        Flight flightFromDB = flightRepository.findBySchemaPropertyValue("flightId", EXISTING_FLIGHT_ID);
        assertEquals(status, flightFromDB.getStatus());
    }

    @Test
    public void shouldAddFlightDetails() {
        Date timestamp = new Date();
        FlightDetail flightDetail = new FlightDetail();
        flightDetail.setTimestamp(timestamp);
        Flight flight = flightRepository.findByFlightId(EXISTING_FLIGHT_ID);
        flight.addFlightDetail(flightDetail);
        flightRepository.saveOnly(flight);
        Flight flightFromDB = flightRepository.findBySchemaPropertyValue("flightId", EXISTING_FLIGHT_ID);
        neo4jTemplate.fetch(flightFromDB.getFlightDetails());
        assertTrue(flightFromDB.getFlightDetails().size() == 1);
        assertTrue(flightFromDB.getFlightDetails()
                .stream()
                .allMatch(
                        detail -> detail.getTimestamp().equals(timestamp)));
    }

    @Test
    public void shouldAddNewArrivalAirPort() {
        Flight flight = flightRepository.findByFlightId(EXISTING_FLIGHT_ID);
        flight.addDestinationAirPort(redirectedAirPort);
        flightRepository.saveOnly(flight);
        Flight flightFromDB = flightRepository.findBySchemaPropertyValue("flightId", EXISTING_FLIGHT_ID);
        int expectedSize = 2;
        assertArrivalAirPort(flightFromDB.getArrivalAirports(), expectedSize);
        assertTrue(flightFromDB.getArrivalAirports()
                .stream()
                .allMatch(
                        dest -> (matchOrdinalNumberAndArrivalAirPort(dest, 2L, redirectedAirPort)
                                    || matchOrdinalNumberAndArrivalAirPort(dest, 1L, arrivalAirPort))
                                && dest.getFlight().equals(flightFromDB)));
    }

    @Test
    public void shouldSaveFlightWithAirLineAndAirPorts() {
        int expectedSize = 1;
        Long flightId = 1L;
        Flight flight = new Flight();
        flight.setFlightId(flightId);
        flight.setDepartureAirport(departureAirPort);
        flight.setAirLine(airLine);
        flight.addDestinationAirPort(arrivalAirPort);
        flightRepository.saveOnly(flight);
        Flight flightFromDB = flightRepository.findBySchemaPropertyValue("flightId", flightId);
        assertNotNull(flightFromDB);
        assertDepartureAirPort(flightFromDB.getDepartureAirport());
        assertAirLine(flightFromDB.getAirLine());
        assertArrivalAirPort(flightFromDB.getArrivalAirports(), expectedSize);
        assertDesitnationAirPort(flightFromDB);
    }

    private void assertDepartureAirPort(AirPort departureAirPort) {
        assertNotNull(departureAirPort);
        assertEquals(this.departureAirPort, departureAirPort);
        assertEquals(DEPARTURE_NAME, departureAirPort.getName());
    }


    private void assertAirLine(AirLine airLine) {
        assertNotNull(airLine);
        assertEquals(this.airLine, airLine);
        assertEquals(AIR_LINE, airLine.getName());
    }

    private void assertArrivalAirPort(Set<DestinationAirPort> arrivalAirports, int expectedSize) {
        assertNotNull(arrivalAirports);
        System.out.println(arrivalAirports.size() +" " + expectedSize);
        assertTrue(arrivalAirports.size() == expectedSize);
    }

    private void assertDesitnationAirPort(Flight flight) {
        assertTrue(flight.getArrivalAirports()
                .stream()
                .allMatch(
                        dest -> matchOrdinalNumberAndArrivalAirPort(dest, 1L, arrivalAirPort)
                                && dest.getFlight().equals(flight)));
    }

    private boolean matchOrdinalNumberAndArrivalAirPort(DestinationAirPort dest, Long expectedOrdinalNumber, AirPort expectedArrivalAirPort) {
        return dest.getOrdinalNumber().compareTo(expectedOrdinalNumber) == 0
                && dest.getArrivalAirPort().equals(expectedArrivalAirPort);
    }
}
