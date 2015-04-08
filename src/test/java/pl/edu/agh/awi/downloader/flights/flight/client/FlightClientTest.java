package pl.edu.agh.awi.downloader.flights.flight.client;

import org.junit.Test;
import pl.edu.agh.awi.downloader.flights.flight.data.FlightResponse;

import static org.junit.Assert.assertNotNull;

public class FlightClientTest {

    public static final String LOAD_BALANCER = "krk.data.fr24.com";

    public static final String ZONE = "na_ne";

    @Test
    public void downloadTest() {
        FlightClient client = new FlightClient();
        FlightResponse flights = client
                .withLoadBalancer(LOAD_BALANCER)
                .withZone(ZONE)
                .getResponse();

        assertNotNull(flights);
        assertNotNull(flights.getFlights());
    }

}