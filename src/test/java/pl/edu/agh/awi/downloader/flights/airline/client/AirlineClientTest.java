package pl.edu.agh.awi.downloader.flights.airline.client;

import org.junit.Test;
import pl.edu.agh.awi.downloader.flights.airline.data.AirlineResponse;

import static org.junit.Assert.assertNotNull;

public class AirlineClientTest {

    @Test
    public void downloadTest() {
        AirlineClient client = new AirlineClient();
        AirlineResponse airlineResponse = client.getResponse();

        assertNotNull(airlineResponse);
        assertNotNull(airlineResponse.getAirlines());
    }
}
