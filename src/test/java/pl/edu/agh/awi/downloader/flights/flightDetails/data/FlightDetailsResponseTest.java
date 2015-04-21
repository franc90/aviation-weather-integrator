package pl.edu.agh.awi.downloader.flights.flightDetails.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FlightDetailsResponseTest {

    private static final double DELTA = .000001;

    @Test
    public void conversionTest() throws URISyntaxException, IOException {
        FlightDetailsResponse response = readValue("/example/json/flightDetails/flightDetails_correctResponse.json");

        assertEquals(response.getFightId(), "5ec8407");
        assertEquals(response.getAircraft(), "Airbus A319-132");
        assertEquals(response.getEta(), new Date(1428227712000L));
        assertEquals(response.getDestinationCity(), "New York, New York John F. Kennedy International Airport");
        assertEquals(response.getDestinationCoords().get(0), 40.639751, DELTA);
        assertEquals(response.getOriginTimezoneOffset(), -5.00, DELTA);
    }

    @Test
    public void additionalFieldAndNotFullDataConversionTest() throws URISyntaxException, IOException {
        FlightDetailsResponse response = readValue("/example/json/flightDetails/flightDetails_notEnoughFields.json");

        assertNotNull(response);
        assertEquals(response.getAircraft(), "Canadair CRJ-700");
    }

    @Test
    public void tooManyFieldsConversionTest() throws IOException, URISyntaxException {
        FlightDetailsResponse response = readValue("/example/json/flightDetails/flightDetails_tooManyFields");

        assertNotNull(response);
        assertEquals(response.getAircraft(), "Airbus A321-231");
        assertEquals(response.getAirline(), "American Airlines");
        assertEquals(response.getEta(), new Date(1429641088000L));
        assertEquals(response.getOriginCity(), "Charlotte, Charlotte Douglas International Airport");
        assertEquals(response.getDestinationIata(), "TPA");
    }

    private FlightDetailsResponse readValue(String path) throws URISyntaxException, IOException {
        URL resource = getClass().getResource(path);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(resource.toURI()), FlightDetailsResponse.class);
    }

}