package pl.edu.agh.awi.downloader.weather.metar.client;

import org.junit.Test;
import pl.edu.agh.awi.downloader.weather.metar.generated.Data;
import pl.edu.agh.awi.downloader.weather.metar.generated.METAR;
import pl.edu.agh.awi.downloader.weather.metar.generated.Response;
import pl.edu.agh.awi.downloader.weather.parameters.RequestParameters;
import pl.edu.agh.awi.downloader.weather.parameters.Stations;
import pl.edu.agh.awi.downloader.weather.parameters.StationsBuilder;

import java.util.List;

import static org.junit.Assert.*;

public class MetarClientTest {

    @Test
    public void testDownloading() {
        RequestParameters<Stations> parameters = new RequestParameters.RequestParametersBuilder<Stations>()
                .setValue(new StationsBuilder()
                        .addStation("EPKK")
                        .addStation("KJFK")
                        .build())
                .setHoursBeforeNow(2)
                .build();

        MetarClient client = new MetarClient();
        Response metarResponse = client.getResponse(parameters);
        assertNull(metarResponse.getErrors().getError());

        Data data = metarResponse.getData();
        assertNotNull(data);

        assertNotEquals((int) data.getNumResults(), 0);

        List<METAR> metars = data.getMETAR();
        assertNotNull(metars);
        assertEquals(metars.size(), (int) data.getNumResults());
    }

}