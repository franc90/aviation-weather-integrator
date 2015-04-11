package pl.edu.agh.awi.downloader.flights.airport.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AirportResponseTest {


    @Test
    public void airportsConvertionTest() throws IOException, URISyntaxException {
        URL resource = getClass().getResource("/example/json/airports/airports_response.json");

        ObjectMapper mapper = new ObjectMapper();
        AirportResponse airportResponse = mapper.readValue(new File(resource.toURI()), AirportResponse.class);

        assertNotNull(airportResponse);
        assertEquals(airportResponse.getVersion(), 1428065130);

        List<Airport> airports = airportResponse.getAirports();
        assertNotNull(airports);
        assertEquals(airports.size(), 8);

        Airport airport = airports.get(2);
        assertEquals(airport.getName(), "New York John F. Kennedy International Airport");
        assertEquals(airport.getIata(), "JFK");
        assertEquals(airport.getIcao(), "KJFK");
        assertEquals(airport.getLatitude(), 40.639751, .000001);
        assertEquals(airport.getLongitude(), -73.778900, .000001);
        assertEquals(airport.getCountry(), "United States");
        assertEquals(airport.getAltitude(), 14);

    }

}