package pl.edu.agh.awi.downloader.flights.airport.client;

import org.springframework.stereotype.Component;
import pl.edu.agh.awi.downloader.flights.AbstractFlightsClient;
import pl.edu.agh.awi.downloader.flights.airport.data.AirportResponse;

@Component
public class AirportClient extends AbstractFlightsClient<AirportResponse, AirportResponse> {

    private final String URL = "http://www.flightradar24.com/_json/airports.php";

    public AirportClient() {
        super(AirportResponse.class);
    }

    @Override
    public AirportResponse getResponse() {
        return load();
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
