package pl.edu.agh.awi.downloader.flights.airline.client;

import pl.edu.agh.awi.downloader.flights.AbstractFlightsClient;
import pl.edu.agh.awi.downloader.flights.airline.data.AirlineResponse;

public class AirlineClient extends AbstractFlightsClient<AirlineResponse, AirlineResponse> {

    private static final String URL = "http://www.flightradar24.com/_json/airlines.php";

    public AirlineClient() {
        super(AirlineResponse.class);
    }


    @Override
    public AirlineResponse getResponse() {
        return load();
    }

    @Override
    protected String getUrl() {
        return URL;
    }
}
