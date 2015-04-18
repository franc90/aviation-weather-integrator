package pl.edu.agh.awi.scheduled.cache;

import java.io.Serializable;
import java.util.Date;

public class CachedFlight implements Serializable{

    private String status;
    private String arrivalIata;
    private String departureIata;
    private Date lastUpdated;

    public CachedFlight(String arrivalIata, String departureIata, Date lastUpdated) {
        this.arrivalIata = arrivalIata;
        this.departureIata = departureIata;
        this.lastUpdated = lastUpdated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getArrivalIata() {
        return arrivalIata;
    }

    public void setArrivalIata(String arrivalIata) {
        this.arrivalIata = arrivalIata;
    }

    public String getDepartureIata() {
        return departureIata;
    }

    public void setDepartureIata(String departureIata) {
        this.departureIata = departureIata;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
