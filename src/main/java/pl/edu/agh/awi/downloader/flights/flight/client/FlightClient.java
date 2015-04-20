package pl.edu.agh.awi.downloader.flights.flight.client;

import org.springframework.stereotype.Component;
import pl.edu.agh.awi.downloader.flights.AbstractFlightsClient;
import pl.edu.agh.awi.downloader.flights.flight.data.FlightResponse;
import pl.edu.agh.awi.downloader.flights.flight.data.converter.FlightResponseConverter;

import java.util.HashMap;
import java.util.Map;

import static java.text.MessageFormat.format;

@Component
public class FlightClient extends AbstractFlightsClient<FlightResponse, HashMap<String, Object>> {

    private static final String URL = "http://{0}/zones/fcgi/{1}_all.json";

    private static Class<HashMap<String, Object>> clazz = (Class<HashMap<String, Object>>) new HashMap<String, Object>().getClass();

    private String loadBalancer;

    private String zone;

    public FlightClient() {
        super(clazz);
    }

    public FlightClient withLoadBalancer(String loadBalancer) {
        this.loadBalancer = loadBalancer;
        return this;
    }

    public FlightClient withZone(String zone) {
        this.zone = zone;
        return this;
    }

    @Override
    public FlightResponse getResponse() {
        Map<String, Object> parsed = load();
        FlightResponse flightResponse = FlightResponseConverter.convert(parsed);

        return flightResponse;
    }

    @Override
    protected String getUrl() {
        return format(URL, loadBalancer, zone);
    }
}
