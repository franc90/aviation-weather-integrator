package pl.edu.agh.awi.downloader.metar.client;

import pl.edu.agh.awi.downloader.AbstractClient;
import pl.edu.agh.awi.downloader.metar.generated.Response;
import pl.edu.agh.awi.downloader.paramters.Stations;

import java.net.URL;

import static java.text.MessageFormat.format;

public class MetarClient extends AbstractClient<Response> {

    public static final String URL = "https://www.aviationweather.gov/adds/dataserver_current/httpparam?dataSource=metars&requestType=retrieve&format=xml&stationString={0}&hoursBeforeNow={1}";

    public MetarClient() {
        super(Response.class);
    }

    public Response getMetarResponse(Stations stations, int hoursBeforeNow) {
        String formattedUrl = format(URL, stations.getStations(), hoursBeforeNow);
        URL url = createUrl(formattedUrl);
        return unmarshallResponse(url);
    }


}
