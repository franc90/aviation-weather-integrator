package pl.edu.agh.awi.loader.converter;

import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.awi.downloader.flights.airline.data.Airline;
import pl.edu.agh.awi.persistence.model.AirLine;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class AirlineConverterTest {

    public static final String NAME = "Delta Air Lines";
    public static final String IATA = "DL";
    public static final String ICAO = "DAL";

    private AirlineConverter converter;

    private Airline airline;

    @Before
    public void init() {
        converter = new AirlineConverter();

        airline = new Airline(NAME, IATA, ICAO);
    }

    @Test
    public void convertOneAirline() {
        AirLine airLine = converter.apply(airline);

        checkAirline(airLine);
    }

    @Test
    public void convertList() {
        List<AirLine> convert = converter.convert(Arrays.asList(airline));

        assertNotNull(convert);
        assertEquals(convert.size(), 1);

        checkAirline(convert.get(0));
    }


    private void checkAirline(AirLine airLine) {
        assertNotNull(airLine);

        assertEquals(airLine.getName(), NAME);
        assertEquals(airLine.getIataCode(), IATA);
        assertEquals(airLine.getIcaoCode(), ICAO);
    }

}