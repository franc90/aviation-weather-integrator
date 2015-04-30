package pl.edu.agh.awi.api.model;

import java.util.Date;
import java.util.Set;

public class FlightAPIObject {

    private String flightId;
    private String status;
    private String aircraft;
    private Date scheduledDepartureTime;
    private Date actualDepartureTime;
    private Date scheduledArrivalTime;
    private Date actualArrivalTime;
    private AirLineAPIObject airLine;
    private AirPortAPIObject departureAirport;

    private Set<AirPortAPIObject> arrivalAirports;

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAircraft() {
        return aircraft;
    }

    public void setAircraft(String aircraft) {
        this.aircraft = aircraft;
    }

    public Date getScheduledDepartureTime() {
        return scheduledDepartureTime;
    }

    public void setScheduledDepartureTime(Date scheduledDepartureTime) {
        this.scheduledDepartureTime = scheduledDepartureTime;
    }

    public Date getActualDepartureTime() {
        return actualDepartureTime;
    }

    public void setActualDepartureTime(Date actualDepartureTime) {
        this.actualDepartureTime = actualDepartureTime;
    }

    public Date getScheduledArrivalTime() {
        return scheduledArrivalTime;
    }

    public void setScheduledArrivalTime(Date scheduledArrivalTime) {
        this.scheduledArrivalTime = scheduledArrivalTime;
    }

    public Date getActualArrivalTime() {
        return actualArrivalTime;
    }

    public void setActualArrivalTime(Date actualArrivalTime) {
        this.actualArrivalTime = actualArrivalTime;
    }

    public AirLineAPIObject getAirLine() {
        return airLine;
    }

    public void setAirLine(AirLineAPIObject airLine) {
        this.airLine = airLine;
    }

    public AirPortAPIObject getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(AirPortAPIObject departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Set<AirPortAPIObject> getArrivalAirports() {
        return arrivalAirports;
    }

    public void setArrivalAirports(Set<AirPortAPIObject> arrivalAirports) {
        this.arrivalAirports = arrivalAirports;
    }
}
