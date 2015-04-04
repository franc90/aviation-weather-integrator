package pl.edu.agh.awi.downloader.weather.airsigmet.client;

import pl.edu.agh.awi.downloader.weather.AbstractClient;
import pl.edu.agh.awi.downloader.weather.airsigmet.generated.Response;
import pl.edu.agh.awi.downloader.weather.parameters.RectangularRegion;

import java.net.URL;

import static java.text.MessageFormat.format;

public class AirSigMetClient extends AbstractClient<Response> {

    public static final String URL = "https://www.aviationweather.gov/adds/dataserver_current/httpparam?dataSource=airsigmets&requestType=retrieve&format=xml&minLat={0}&minLon={1}&maxLat={2}&maxLon={3}&hoursBeforeNow={4}";

    public AirSigMetClient() {
        super(Response.class);
    }

    public Response getAirSigMetResponse(RectangularRegion region, int hoursBeforeNow) {
        String formattedUrl = format(URL, region.getMinimalLatitude(), region.getMinimalLongitude(), region.getMaximalLatitude(), region.getMaximalLongitude(), hoursBeforeNow);
        URL url = createUrl(formattedUrl);
        return unmarshallResponse(url);
    }


}
