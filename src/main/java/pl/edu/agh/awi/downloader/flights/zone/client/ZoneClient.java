package pl.edu.agh.awi.downloader.flights.zone.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.edu.agh.awi.downloader.exceptions.MalformedUrlException;
import pl.edu.agh.awi.downloader.flights.zone.data.ZoneResponse;
import pl.edu.agh.awi.downloader.flights.zone.data.converter.ZoneResponseConverter;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ZoneClient {

    public static final String URL = "http://www.flightradar24.com/js/zones.js.php";

    private ObjectMapper objectMapper = new ObjectMapper();

    public ZoneResponse getZones() {
        Map<String, Object> zones = loadNodes();
        ZoneResponse zoneResponse = ZoneResponseConverter.convert(zones);

        return zoneResponse;
    }

    private Map<String, Object> loadNodes() {
        try {
            return objectMapper.readValue(new URL(URL), HashMap.class);
        } catch (IOException e) {
            throw new MalformedUrlException("Error while getting zones", e);
        }
    }

}
