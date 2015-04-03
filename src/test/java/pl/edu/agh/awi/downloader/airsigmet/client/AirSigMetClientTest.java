package pl.edu.agh.awi.downloader.airsigmet.client;

import org.junit.Test;
import pl.edu.agh.awi.downloader.airsigmet.generated.AIRSIGMET;
import pl.edu.agh.awi.downloader.airsigmet.generated.Data;
import pl.edu.agh.awi.downloader.airsigmet.generated.Response;
import pl.edu.agh.awi.downloader.paramters.RectangularRegion;
import pl.edu.agh.awi.downloader.paramters.RectangularRegionBuilder;

import java.util.List;

import static org.junit.Assert.*;

public class AirSigMetClientTest {

    @Test
    public void testDownloading() {
        AirSigMetClient client = new AirSigMetClient();
        RectangularRegion region = new RectangularRegionBuilder()
                .setMinimalLatitude(20)
                .setMaximalLatitude(50)
                .setMinimalLongitude(-126)
                .setMaximalLongitude(-66)
                .createRectangularRegion();

        Response airsigmetResponse = client.getAirSigMetResponse(region, 2);
        assertNull(airsigmetResponse.getErrors().getError());

        Data data = airsigmetResponse.getData();
        assertNotNull(data);

        assertNotEquals((int) data.getNumResults(), 0);

        List<AIRSIGMET> airsigmets = data.getAIRSIGMET();
        assertNotNull(airsigmets);
        assertEquals(airsigmets.size(), (int) data.getNumResults());
    }

}