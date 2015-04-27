package pl.edu.agh.awi.downloader.flights.flightDetails.client;

import org.springframework.stereotype.Component;
import pl.edu.agh.awi.downloader.flights.AbstractFlightsClient;
import pl.edu.agh.awi.downloader.flights.flightDetails.data.FlightDetailsResponse;

import static java.text.MessageFormat.format;

@Component
public class FlightDetailsClient extends AbstractFlightsClient<FlightDetailsResponse, FlightDetailsResponse> {

    private static final String URL = "http://{0}/_external/planedata_json.1.4.php?f={1}";

    private String loadBalancer;

    private String flightId;

    public FlightDetailsClient() {
        super(FlightDetailsResponse.class);
    }

    public FlightDetailsClient withLoadBalancer(String loadBalancer) {
        this.loadBalancer = loadBalancer;
        return this;
    }

    public FlightDetailsClient withFlightId(String flightId) {
        this.flightId = flightId;
        return this;
    }

    @Override
    public FlightDetailsResponse getResponse() {
        return load();
    }

    @Override
    protected String getUrl() {
        return format(URL, loadBalancer, flightId);
    }

    @Override
    protected String getParameter() {
        return flightId;
    }
}
