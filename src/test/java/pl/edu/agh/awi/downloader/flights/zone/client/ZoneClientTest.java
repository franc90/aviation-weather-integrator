package pl.edu.agh.awi.downloader.flights.zone.client;

import org.junit.Test;
import pl.edu.agh.awi.downloader.flights.zone.data.converter.ZoneResponse;

import static org.junit.Assert.*;

public class ZoneClientTest {

    @Test
    public void downloadTest() {
        ZoneClient client = new ZoneClient();
        ZoneResponse zones = client.getZones();

        assertNotNull(zones);
        assertNotNull(zones.getZones());
    }

}