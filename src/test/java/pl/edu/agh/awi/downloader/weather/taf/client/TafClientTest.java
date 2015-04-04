package pl.edu.agh.awi.downloader.weather.taf.client;

import org.junit.Test;
import pl.edu.agh.awi.downloader.weather.parameters.RequestParameters;
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
        RequestParameters<Stations> parameters = new RequestParameters.RequestParametersBuilder<Stations>()
                .setValue(new StationsBuilder()
                        .addStation("EPKK")
                        .build())
                .setHoursBeforeNow(2)
                .build();

        TafClient client = new TafClient();
        Response tafResponse = client.getResponse(parameters);
        assertNull(tafResponse.getErrors().getError());

        Data data = tafResponse.getData();
        assertNotNull(data);

        assertNotEquals((int) data.getNumResults(), 0);

        List<TAF> tafs = data.getTAF();
        assertNotNull(tafs);
        assertEquals(tafs.size(), (int) data.getNumResults());
    }

}