package pl.edu.agh.awi.loader.converter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import pl.edu.agh.awi.downloader.flights.airport.data.Airport;
import pl.edu.agh.awi.persistence.model.AirPort;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class AirportConverterTest {

    @Configuration
    @ComponentScan(basePackages = {"pl.edu.agh.awi.loader.converter", "pl.edu.agh.awi.loader.file_loader"})
    static class ContextConfiguration {
    }

    public static final String NAME = "John Paul II International Airport Kraków-Balice";

    public static final String IATA = "KRK";

    public static final String ICAO = "EPKK";

    public static final float LAT = 50.07f;

    public static final float LON = 19.8f;

    public static final String COUNTRY = "POL";

    public static final int ALT = 237;

    public static final String CITY = "Kraków";

    public static final int RUNWAYS_CNT = 1;

    public static final float DELTA = 0.01f;

    @Autowired
    private AirportConverter converter;

    private Airport airport;

    @Before
    public void init() {
        airport = new Airport(NAME, IATA, ICAO, LAT, LON, COUNTRY, ALT);
    }

    @Test
    public void checkConversion() {
        AirPort airPort = converter.apply(airport);

        checkAirport(airPort);
    }

    @Test
    public void checkListConversion() {
        assertNotNull(converter);
        List<AirPort> convert = converter.convert(Arrays.asList(airport));

        assertNotNull(convert);
        assertEquals(convert.size(), 1);
        checkAirport(convert.get(0));
    }

    private void checkAirport(AirPort airPort) {
        assertEquals(airPort.getName(), NAME);
        assertEquals(airPort.getIataCode(), IATA);
        assertEquals(airPort.getIcaoCode(), ICAO);
        assertEquals(airPort.getLatitude().floatValue(), LAT, DELTA);
        assertEquals(airPort.getLongitude().floatValue(), LON, DELTA);
        assertEquals(airPort.getCountry(), COUNTRY);
        assertEquals(airPort.getCity(), CITY);
        assertEquals((int) airPort.getNumberOfRunways(), RUNWAYS_CNT);
    }

}