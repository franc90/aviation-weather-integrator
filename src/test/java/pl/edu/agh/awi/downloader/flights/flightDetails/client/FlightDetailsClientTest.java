package pl.edu.agh.awi.downloader.flights.flightDetails.client;

import org.junit.Ignore;
import org.junit.Test;
import pl.edu.agh.awi.downloader.flights.flightDetails.data.FlightDetailsResponse;

import static org.junit.Assert.*;

public class FlightDetailsClientTest {

    public static final String LOAD_BALANCER = "krk.data.fr24.com";

    public static final String FLIGHT_ID = "5ecc5d6";

    @Test
    @Ignore // when uncommented, be sure to provide actual FLIGHT_ID
    public void downloadTest() {
        FlightDetailsClient client = new FlightDetailsClient();

        FlightDetailsResponse details = client.getFlightDetails(LOAD_BALANCER, FLIGHT_ID);

        assertNotNull(details);
        assertEquals(details.getFightId(), FLIGHT_ID);
    }

}