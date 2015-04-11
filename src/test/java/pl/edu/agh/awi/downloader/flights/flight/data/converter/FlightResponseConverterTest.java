package pl.edu.agh.awi.downloader.flights.flight.data.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import pl.edu.agh.awi.downloader.flights.flight.data.Flight;
import pl.edu.agh.awi.downloader.flights.flight.data.FlightResponse;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FlightResponseConverterTest {

    private static final Double DELTA = .001;

    @Test
    public void flightConversionTest() throws URISyntaxException, IOException {
        URL resource = getClass().getResource("/example/json/flights/flights_response.json");

        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> parsed = mapper.readValue(new File(resource.toURI()), HashMap.class);

        FlightResponse convert = FlightResponseConverter.convert(parsed);

        assertNotNull(convert);
        assertEquals(convert.getVersion(), 4);
        assertEquals(convert.getFlightsCount(), 300);

        List<Flight> flights = convert.getFlights();
        assertNotNull(flights);
        assertEquals(flights.size(), 300);

        Flight flight = flights.get(10);
        assertNotNull(flight);

        assertEquals(flight.getFlightId(), "5ebccf4");
        assertEquals(flight.getRegistration(), "");
        assertEquals(flight.getLatitude(), 36.799, DELTA);
        assertEquals(flight.getLongitude(), -87.57, DELTA);
        assertEquals(flight.getTrack(), 130);
        assertEquals(flight.getAltitude(), 40990);
        assertEquals(flight.getSpeed(), 504);
        assertEquals(flight.getSquawk(), "0000");
        assertEquals(flight.getRadar(), "T-MLAT2");
        assertEquals(flight.getAircraft(), "C56X");
        assertEquals(flight.getRegistration2(), "");
        assertEquals(flight.getDepartureIata(), "");
        assertEquals(flight.getArrivalIata(), "");
        assertEquals(flight.getFlightDesignator(), "");
        assertEquals(flight.getValue1(), 0);
        assertEquals(flight.getVerticalSpeed(), 0);
        assertEquals(flight.getFlightTracker(), "C56X");
        assertEquals(flight.getValue2(), 0);
    }
}