package pl.edu.agh.awi.downloader.taf.client;

import pl.edu.agh.awi.downloader.AbstractClient;
import pl.edu.agh.awi.downloader.paramters.Stations;
import pl.edu.agh.awi.downloader.taf.generated.Response;

import static java.text.MessageFormat.format;

public class TafClient extends AbstractClient<Response> {

    public static final String URL = "https://www.aviationweather.gov/adds/dataserver_current/httpparam?dataSource=tafs&requestType=retrieve&format=xml&stationString={0}&hoursBeforeNow={1}";

    public TafClient() {
        super(Response.class);
    }

    public Response getTafResponse(Stations stations, int hoursBeforeNow) {
        String formattedUrl = format(URL, stations.getStations(), hoursBeforeNow);
        java.net.URL url = createUrl(formattedUrl);
        return unmarshallResponse(url);
    }

}
