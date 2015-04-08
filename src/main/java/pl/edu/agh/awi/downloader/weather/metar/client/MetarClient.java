package pl.edu.agh.awi.downloader.weather.metar.client;

import pl.edu.agh.awi.downloader.weather.AbstractWeatherClient;
import pl.edu.agh.awi.downloader.weather.metar.generated.Response;
import pl.edu.agh.awi.downloader.weather.parameters.RequestParameters;
import pl.edu.agh.awi.downloader.weather.parameters.Stations;

import static java.text.MessageFormat.format;

public class MetarClient extends AbstractWeatherClient<Response, Stations> {

    public static final String URL = "https://www.aviationweather.gov/adds/dataserver_current/httpparam?dataSource=metars&requestType=retrieve&format=xml&stationString={0}&hoursBeforeNow={1}";

    public MetarClient() {
        super(Response.class);
    }

    @Override
    protected String formatUrl(RequestParameters<Stations> parameters) {
        Stations stations = parameters.getValue();
        int hoursBeforeNow = parameters.getHoursBeforeNow();

        return format(URL, stations.getStations(), hoursBeforeNow);
    }
}
