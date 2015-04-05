package pl.edu.agh.awi.downloader.flights.flightDetails.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.edu.agh.awi.downloader.exceptions.JsonTreeReadingException;
import pl.edu.agh.awi.downloader.flights.flightDetails.data.FlightDetailsResponse;

import java.io.IOException;
import java.net.URL;

import static java.text.MessageFormat.format;

public class FlightDetailsClient {

    public static final String URL = "http://{0}/_external/planedata_json.1.4.php?f={1}";

    private ObjectMapper objectMapper = new ObjectMapper();

    public FlightDetailsResponse getFlightDetails(String loadBalancer, String flightId) {
        String formattedUrl = format(URL, loadBalancer, flightId);
        try {
            return objectMapper.readValue(new URL(formattedUrl), FlightDetailsResponse.class);
        } catch (IOException e) {
            throw new JsonTreeReadingException("Could not read flight details for " + flightId, e);
        }
    }
}
