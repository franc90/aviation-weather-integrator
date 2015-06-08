package pl.edu.agh.awi.api.model;

import java.util.Set;

public class AirPortAPIObject {

    private String name;
    private String city;
    private String country;
    private Integer numberOfRunways;
    private Double longitude;
    private Double latitude;
    private String iataCode;
    private String icaoCode;

    private Set<MetarAPIObject> metars;
    private Set<TafAPIObject> tafs;
    private Set<AirSigmetAPIObject> airSigmets;
    private Set<FlightAPIObject> outgoingFlights;
    private Set<FlightAPIObject> incomingFlights;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getNumberOfRunways() {
        return numberOfRunways;
    }

    public void setNumberOfRunways(Integer numberOfRunways) {
        this.numberOfRunways = numberOfRunways;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public String getIcaoCode() {
        return icaoCode;
    }

    public void setIcaoCode(String icaoCode) {
        this.icaoCode = icaoCode;
    }

    public Set<MetarAPIObject> getMetars() {
        return metars;
    }

    public void setMetars(Set<MetarAPIObject> metars) {
        this.metars = metars;
    }

    public Set<TafAPIObject> getTafs() {
        return tafs;
    }

    public void setTafs(Set<TafAPIObject> tafs) {
        this.tafs = tafs;
    }

    public Set<AirSigmetAPIObject> getAirSigmets() {
        return airSigmets;
    }

    public void setAirSigmets(Set<AirSigmetAPIObject> airSigmets) {
        this.airSigmets = airSigmets;
    }

    public Set<FlightAPIObject> getOutgoingFlights() {
        return outgoingFlights;
    }

    public void setOutgoingFlights(Set<FlightAPIObject> outgoingFlights) {
        this.outgoingFlights = outgoingFlights;
    }

    public Set<FlightAPIObject> getIncomingFlights() {
        return incomingFlights;
    }

    public void setIncomingFlights(Set<FlightAPIObject> incomingFlights) {
        this.incomingFlights = incomingFlights;
    }
}
