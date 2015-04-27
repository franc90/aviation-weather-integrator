package pl.edu.agh.awi.downloader.flights.zone.client;

import org.springframework.stereotype.Component;
import pl.edu.agh.awi.downloader.flights.AbstractFlightsClient;
import pl.edu.agh.awi.downloader.flights.zone.data.ZoneResponse;
import pl.edu.agh.awi.downloader.flights.zone.data.converter.ZoneResponseConverter;

import java.util.HashMap;
import java.util.Map;

@Component
public class ZoneClient extends AbstractFlightsClient<ZoneResponse, HashMap<String, Object>> {

    private static final String URL = "http://www.flightradar24.com/js/zones.js.php";

    private static Class<HashMap<String, Object>> clazz = (Class<HashMap<String, Object>>) new HashMap<String, Object>().getClass();

    public ZoneClient() {
        super(clazz);
    }

    @Override
    public ZoneResponse getResponse() {
        Map<String, Object> zones = load();
        ZoneResponse zoneResponse = ZoneResponseConverter.convert(zones);

        return zoneResponse;
    }

    @Override
    protected String getUrl() {
        return URL;
    }

    @Override
    protected String getParameter() {
        return "";
    }
}
