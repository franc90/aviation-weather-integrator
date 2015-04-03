package pl.edu.agh.awi.downloader.metar.client;

import org.junit.Test;
import pl.edu.agh.awi.downloader.metar.generated.Data;
import pl.edu.agh.awi.downloader.metar.generated.METAR;
import pl.edu.agh.awi.downloader.metar.generated.Response;
import pl.edu.agh.awi.downloader.paramters.Stations;
import pl.edu.agh.awi.downloader.paramters.StationsBuilder;

import java.util.List;

import static org.junit.Assert.*;

public class MetarClientTest {

    @Test
    public void testDownloading() {
        MetarClient client = new MetarClient();
        Stations stations = new StationsBuilder().
                addStation("EPKK")
                .createStations();

        Response metarResponse = client.getMetarResponse(stations, 2);
        assertNull(metarResponse.getErrors().getError());

        Data data = metarResponse.getData();
        assertNotNull(data);

        assertNotEquals((int) data.getNumResults(), 0);

        List<METAR> metars = data.getMETAR();
        assertNotNull(metars);
        assertEquals(metars.size(), (int) data.getNumResults());
    }

}