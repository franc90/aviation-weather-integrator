package pl.edu.agh.awi.downloader.flights.airline.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AirlineResponseTest {

    @Test
    public void airlinesConversionTest() throws URISyntaxException, IOException {
        URL resource = getClass().getResource("/example/json/airlines/airlines_response.json");

        ObjectMapper mapper = new ObjectMapper();
        AirlineResponse airlineResponse = mapper.readValue(new File(resource.toURI()), AirlineResponse.class);

        assertNotNull(airlineResponse);
        assertEquals(airlineResponse.getVersion(), 1427897860);

        List<Airline> airlines = airlineResponse.getAirlines();
        assertNotNull(airlines);
        assertEquals(airlines.size(), 21);

        Airline airline = airlines.get(6);
        assertEquals(airline.getName(), "Aeroméxico Connect");
        assertEquals(airline.getIata(), "5D");
        assertEquals(airline.getIcao(), "SLI");

        airline = airlines.get(13);
        assertEquals(airline.getName(), "MASwings");
        assertEquals(airline.getIata(), "");
        assertEquals(airline.getIcao(), "MWG");

    }
}
