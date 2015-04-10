package pl.edu.agh.awi.downloader.flights.flight.data;

import java.util.List;

public class FlightResponse {

    private final long version;

    private final long flightsCount;

    private final List<Flight> flights;

    public FlightResponse(long version, long flightsCount, List<Flight> flights) {
        this.version = version;
        this.flightsCount = flightsCount;
        this.flights = flights;
    }

    public long getVersion() {
        return version;
    }

    public long getFlightsCount() {
        return flightsCount;
    }

    public List<Flight> getFlights() {
        return flights;
    }
}
