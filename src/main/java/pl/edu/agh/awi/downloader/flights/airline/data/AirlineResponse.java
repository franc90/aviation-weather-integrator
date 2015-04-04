package pl.edu.agh.awi.downloader.flights.airline.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AirlineResponse {

    private final long version;

    private final List<Airline> airlines;

    public AirlineResponse(@JsonProperty("version")long version, @JsonProperty("rows")List<Airline> airlines) {
        this.version = version;
        this.airlines = airlines;
    }

    public long getVersion() {
        return version;
    }

    public List<Airline> getAirlines() {
        return airlines;
    }
}
