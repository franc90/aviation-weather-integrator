package pl.edu.agh.awi.downloader.weather.taf.client;

import org.junit.Test;
import pl.edu.agh.awi.downloader.weather.parameters.Stations;
import pl.edu.agh.awi.downloader.weather.parameters.StationsBuilder;
import pl.edu.agh.awi.downloader.weather.taf.generated.Data;
import pl.edu.agh.awi.downloader.weather.taf.generated.Response;
import pl.edu.agh.awi.downloader.weather.taf.generated.TAF;

import java.util.List;

import static org.junit.Assert.*;

public class TafClientTest {

    @Test
    public void testDownloading() {
        TafClient client = new TafClient();
        Stations stations = new StationsBuilder().
                addStation("EPKK")
                .createStations();

        Response tafResponse = client.getTafResponse(stations, 2);
        assertNull(tafResponse.getErrors().getError());

        Data data = tafResponse.getData();
        assertNotNull(data);

        assertNotEquals((int) data.getNumResults(), 0);

        List<TAF> tafs = data.getTAF();
        assertNotNull(tafs);
        assertEquals(tafs.size(), (int) data.getNumResults());
    }

}