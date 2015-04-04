package pl.edu.agh.awi.downloader.weather.airsigmet.client;

import org.junit.Test;
import pl.edu.agh.awi.downloader.weather.airsigmet.generated.AIRSIGMET;
import pl.edu.agh.awi.downloader.weather.airsigmet.generated.Data;
import pl.edu.agh.awi.downloader.weather.airsigmet.generated.Response;
import pl.edu.agh.awi.downloader.weather.parameters.RectangularRegion;
import pl.edu.agh.awi.downloader.weather.parameters.RectangularRegionBuilder;
import pl.edu.agh.awi.downloader.weather.parameters.RequestParameters;

import java.util.List;

import static org.junit.Assert.*;

public class AirSigMetClientTest {

    @Test
    public void testDownloading() {
        RequestParameters<RectangularRegion> parameters = new RequestParameters.RequestParametersBuilder<RectangularRegion>()
                .setValue(new RectangularRegionBuilder()
                        .setMinimalLatitude(20)
                        .setMaximalLatitude(50)
                        .setMinimalLongitude(-126)
                        .setMaximalLongitude(-66)
                        .build())
                .setHoursBeforeNow(2)
                .build();

        AirSigMetClient client = new AirSigMetClient();
        Response airsigmetResponse = client.getResponse(parameters);
        assertNull(airsigmetResponse.getErrors().getError());

        Data data = airsigmetResponse.getData();
        assertNotNull(data);

        assertNotEquals((int) data.getNumResults(), 0);

        List<AIRSIGMET> airsigmets = data.getAIRSIGMET();
        assertNotNull(airsigmets);
        assertEquals(airsigmets.size(), (int) data.getNumResults());
    }

}