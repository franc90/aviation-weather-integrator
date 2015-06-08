package pl.edu.agh.awi.api.model;

import java.util.Set;

public class AirLineAPIObject {

    private String name;
    private String iataCode;
    private String icaoCode;

    private Set<FlightAPIObject> flights;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Set<FlightAPIObject> getFlights() {
        return flights;
    }

    public void setFlights(Set<FlightAPIObject> flights) {
        this.flights = flights;
    }
}
