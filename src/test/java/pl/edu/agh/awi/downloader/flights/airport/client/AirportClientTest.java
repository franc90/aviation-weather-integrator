package pl.edu.agh.awi.downloader.flights.airport.client;

import org.junit.Test;
import pl.edu.agh.awi.downloader.flights.airport.data.AirportResponse;

import static org.junit.Assert.assertNotNull;

public class AirportClientTest {

    @Test
    public void testDownloading() {
        AirportClient client = new AirportClient();
        AirportResponse airports = client.getResponse();

        assertNotNull(airports);
        assertNotNull(airports.getAirports());
    }


}