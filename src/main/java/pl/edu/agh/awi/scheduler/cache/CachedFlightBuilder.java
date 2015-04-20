package pl.edu.agh.awi.scheduler.cache;

import java.util.Date;

public class CachedFlightBuilder {
    private String arrivalIata;
    private String departureIata;
    private Date lastUpdated;

    public CachedFlightBuilder setArrivalIata(String arrivalIata) {
        this.arrivalIata = arrivalIata;
        return this;
    }

    public CachedFlightBuilder setDepartureIata(String departureIata) {
        this.departureIata = departureIata;
        return this;
    }

    public CachedFlightBuilder setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public CachedFlight build() {
        return new CachedFlight(arrivalIata, departureIata, lastUpdated);
    }
}