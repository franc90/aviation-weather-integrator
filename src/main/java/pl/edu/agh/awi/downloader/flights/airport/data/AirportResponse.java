package pl.edu.agh.awi.downloader.flights.airport.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AirportResponse {

    private final long version;

    private final List<Airport> airports;

    public AirportResponse(@JsonProperty("version") long version, @JsonProperty("rows") List<Airport> rows) {
        this.version = version;
        this.airports = rows;
    }

    public long getVersion() {
        return version;
    }

    public List<Airport> getAirports() {
        return airports;
    }
}
