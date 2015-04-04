package pl.edu.agh.awi.downloader.flights.flight.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.edu.agh.awi.downloader.exceptions.MalformedUrlException;
import pl.edu.agh.awi.downloader.flights.flight.data.FlightResponse;
import pl.edu.agh.awi.downloader.flights.flight.data.converter.FlightResponseConverter;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static java.text.MessageFormat.format;

public class FlightClient {

    public static final String URL = "http://{0}/zones/fcgi/{1}_all.json";

    private ObjectMapper objectMapper = new ObjectMapper();

    public FlightResponse getFlights(String loadBalancer, String zone) {
        String formattedUrl = format(URL, loadBalancer, zone);
        Map<String, Object> parsed = loadNodes(formattedUrl);
        FlightResponse flightResponse = FlightResponseConverter.convert(parsed);

        return flightResponse;
    }

    private Map<String, Object> loadNodes(String url) {
        try {
            return objectMapper.readValue(new URL(url), HashMap.class);
        } catch (IOException e) {
            throw new MalformedUrlException("Error while getting flights", e);
        }
    }

}
