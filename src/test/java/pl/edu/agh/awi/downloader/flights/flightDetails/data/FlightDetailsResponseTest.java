package pl.edu.agh.awi.downloader.flights.flightDetails.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class FlightDetailsResponseTest {

    private static final double DELTA = .000001;

    @Test
    public void conversionTest() throws URISyntaxException, IOException {
        URL resource = getClass().getResource("/example/json/flightDetails/flightDetails_response.json");

        ObjectMapper mapper = new ObjectMapper();
        FlightDetailsResponse response = mapper.readValue(new File(resource.toURI()), FlightDetailsResponse.class);

        assertEquals(response.getFightId(), "5ec8407");
        assertEquals(response.getAircraft(), "Airbus A319-132");
        assertEquals(response.getEta(), new Date(1428227712000L));
        assertEquals(response.getDestinationCity(), "New York, New York John F. Kennedy International Airport");
        assertEquals(response.getDestinationCoords().get(0), 40.639751, DELTA);
        assertEquals(response.getOriginTimezoneOffset(), -5.00, DELTA);
    }

}