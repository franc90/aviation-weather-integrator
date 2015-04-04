package pl.edu.agh.awi.downloader.flights.airline.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.edu.agh.awi.downloader.exceptions.JsonTreeReadingException;
import pl.edu.agh.awi.downloader.flights.airline.data.AirlineResponse;

import java.io.IOException;
import java.net.URL;

public class AirlineClient {

    public static final String URL = "http://www.flightradar24.com/_json/airlines.php";

    private ObjectMapper objectMapper = new ObjectMapper();

    public AirlineResponse getAirlines() {
        try {
            return objectMapper.readValue(new URL(URL), AirlineResponse.class);
        } catch (IOException e) {
            throw new JsonTreeReadingException("Could not read airports", e);
        }
    }
}
