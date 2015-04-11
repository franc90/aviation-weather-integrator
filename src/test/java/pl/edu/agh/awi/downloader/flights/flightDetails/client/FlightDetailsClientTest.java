package pl.edu.agh.awi.downloader.flights.flightDetails.client;

import org.junit.Test;
import pl.edu.agh.awi.downloader.flights.flightDetails.data.FlightDetailsResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FlightDetailsClientTest {

    public static final String LOAD_BALANCER = "krk.data.fr24.com";

    public static final String FLIGHT_ID = "5ecc5d6";

    @Test
    // if does not pass, make sure flight with given flightId exists.
    public void downloadTest() {
        FlightDetailsClient client = new FlightDetailsClient();

        FlightDetailsResponse details = client
                .withLoadBalancer(LOAD_BALANCER)
                .withFlightId(FLIGHT_ID)
                .getResponse();

        assertNotNull(details);
        assertEquals(details.getFightId(), FLIGHT_ID);
    }

}