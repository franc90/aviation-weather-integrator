package pl.edu.agh.awi.downloader.flights.airport.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.edu.agh.awi.downloader.exceptions.JsonTreeReadingException;
import pl.edu.agh.awi.downloader.flights.airport.data.AirportResponse;

import java.io.IOException;
import java.net.URL;

public class AirportClient {

    public static final String URL = "http://www.flightradar24.com/_json/airports.php";

    private ObjectMapper objectMapper = new ObjectMapper();

    public AirportResponse getAirports() {
        try {
            return objectMapper.readValue(new URL(URL), AirportResponse.class);
        } catch (IOException e) {
            throw new JsonTreeReadingException("Could not read airports", e);
        }
    }
}
