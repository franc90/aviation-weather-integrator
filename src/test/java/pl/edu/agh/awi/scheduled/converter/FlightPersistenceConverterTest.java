package pl.edu.agh.awi.scheduled.converter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import pl.edu.agh.awi.downloader.exceptions.FlightTaskException;
import pl.edu.agh.awi.downloader.flights.flight.data.Flight;
import pl.edu.agh.awi.persistence.model.AirLine;
import pl.edu.agh.awi.persistence.model.AirPort;
import pl.edu.agh.awi.persistence.model.DestinationAirPort;
import pl.edu.agh.awi.persistence.repositories.AirLineRepository;
import pl.edu.agh.awi.persistence.repositories.AirPortRepository;

import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class FlightPersistenceConverterTest {

    public static final String DELTA_IATA = "DL";
    public static final String DELTA_ICAO = "DEL";
    public static final String DELTA_NAME = "Delta Air Lines";
    public static final String KRK_IATA = "KRK";
    public static final String KRK_ICAO = "EPKK";
    public static final String KRK_CITY = "Kraków";
    public static final String KRK_COUNTRY = "Poland";
    public static final String JFK_IATA = "JFK";
    public static final String JFK_ICAO = "KJFK";
    public static final String JFK_CITY = "New York";
    public static final String JFK_COUNTRY = "United States";
    public static final String AIRCRAFT = "A330";
    public static final String FLIGHT_ID = "D65AS2";

    @Configuration
    @ComponentScan(basePackages = {"pl.edu.agh.awi.scheduled.converter"})
    static class ContextConfiguration {

        @Bean
        public AirPortRepository airPortRepository() {
            AirPortRepository airPortRepository = Mockito.mock(AirPortRepository.class);
            return airPortRepository;
        }

        @Bean
        public AirLineRepository airLineRepository() {
            AirLineRepository airLineRepository = Mockito.mock(AirLineRepository.class);
            return airLineRepository;
        }

    }

    @Autowired
    private FlightPersistenceConverter converter;

    @Autowired
    private AirPortRepository airPortRepository;

    @Autowired
    private AirLineRepository airLineRepository;

    private Flight source;

    private AirLine delta;

    private AirPort krk;

    private AirPort jfk;

    @Before
    public void init() {
        delta = new AirLine();
        delta.setIataCode(DELTA_IATA);
        delta.setIcaoCode(DELTA_ICAO);
        delta.setName(DELTA_NAME);

        krk = new AirPort();
        krk.setIataCode(KRK_IATA);
        krk.setIcaoCode(KRK_ICAO);
        krk.setCity(KRK_CITY);
        krk.setCountry(KRK_COUNTRY);

        jfk = new AirPort();
        jfk.setIataCode(JFK_IATA);
        jfk.setIcaoCode(JFK_ICAO);
        jfk.setCity(JFK_CITY);
        jfk.setCountry(JFK_COUNTRY);

        source = new Flight.FlightBuilder()
                .setAircraft(AIRCRAFT)
                .setArrivalIata(JFK_IATA)
                .setDepartureIata(KRK_IATA)
                .setFlightDesignator(DELTA_IATA + "1234")
                .setFlightId(FLIGHT_ID)
                .createFlight();
    }

    @Test(expected = FlightTaskException.class)
    public void shouldThrowFlightTaskExcetpion() {
        when(airLineRepository.findByIataCode(anyString())).thenReturn(delta);
        when(airPortRepository.findByIcaoCode(KRK_ICAO)).thenReturn(null);
        when(airPortRepository.findByIcaoCode(JFK_ICAO)).thenReturn(null);
        when(airPortRepository.findByIataCode(KRK_IATA)).thenReturn(null);
        when(airPortRepository.findByIataCode(JFK_IATA)).thenReturn(null);

        converter.convert(source);
    }

    @Test
    public void shouldConvertSingleFlight() {
        when(airLineRepository.findByIataCode(anyString())).thenReturn(delta);
        when(airPortRepository.findByIcaoCode(KRK_ICAO)).thenReturn(krk);
        when(airPortRepository.findByIcaoCode(JFK_ICAO)).thenReturn(jfk);
        when(airPortRepository.findByIataCode(KRK_IATA)).thenReturn(krk);
        when(airPortRepository.findByIataCode(JFK_IATA)).thenReturn(jfk);

        pl.edu.agh.awi.persistence.model.Flight flight = converter.convert(source);

        assertNotNull(flight);
        assertEquals(flight.getAircraft(), AIRCRAFT);
        assertEquals(flight.getAirLine(), delta);
        assertEquals(flight.getDepartureAirport(), krk);
        assertNotNull(flight.getArrivalAirports());
        assertEquals(flight.getArrivalAirports().size(), 1);
        assertTrue(contains(flight.getArrivalAirports(), jfk));
        assertEquals(flight.getFlightId(), FLIGHT_ID);
    }

    private boolean contains(Set<DestinationAirPort> arrivalAirports, AirPort airPort) {
        for (DestinationAirPort arrivalAirport : arrivalAirports) {
            if (krk.equals(arrivalAirport.getArrivalAirPort())) {
                return true;
            }
        }
        return false;
    }

}