package pl.edu.agh.awi.downloader.weather.taf.client;

import org.springframework.stereotype.Component;
import pl.edu.agh.awi.downloader.weather.AbstractWeatherClient;
import pl.edu.agh.awi.downloader.weather.parameters.RequestParameters;
import pl.edu.agh.awi.downloader.weather.parameters.Stations;
import pl.edu.agh.awi.downloader.weather.taf.generated.Response;

import static java.text.MessageFormat.format;

@Component
public class TafClient extends AbstractWeatherClient<Response, Stations> {

    public static final String URL = "https://www.aviationweather.gov/adds/dataserver_current/httpparam?dataSource=tafs&requestType=retrieve&format=xml&stationString={0}&hoursBeforeNow={1}";

    public TafClient() {
        super(Response.class);
    }

    @Override
    protected String formatUrl(RequestParameters<Stations> parameters) {
        Stations stations = parameters.getValue();
        int hoursBeforeNow = parameters.getHoursBeforeNow();

        return format(URL, stations.getStations(), hoursBeforeNow);
    }
}
