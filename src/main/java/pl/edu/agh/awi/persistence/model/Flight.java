package pl.edu.agh.awi.persistence.model;

import com.google.common.collect.Sets;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.*;

import java.util.Date;
import java.util.Set;

@NodeEntity
public class Flight {

    @GraphId
    private Long id;

    @Indexed(unique = true)
    private Long flightId;
    private FlightStatus status;
    private String aircraft;
    private Date scheduledDepartureTime;
    private Date actualDepartureTime;
    private Date scheduledArrivalTime;
    private Date actualArrivalTime;

    @RelatedTo(type = "belongs_to")
    private AirLine airLine;

    @RelatedTo(type = "from", direction = Direction.INCOMING)
    private AirPort departureAirport;

    @RelatedToVia(type = "to")
    private Set<DestinationAirPort> arrivalAirports = Sets.newHashSet();

    @RelatedTo(type = "flight_details")
    private Set<FlightDetail> details = Sets.newHashSet();

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public void setStatus(FlightStatus status) {
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

    public AirPort getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(AirPort departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Set<DestinationAirPort> getArrivalAirports() {
        return arrivalAirports;
    }

    public Set<FlightDetail> getDetails() {
        return details;
    }

    public AirLine getAirLine() {
        return airLine;
    }

    public void setAirLine(AirLine airLine) {
        this.airLine = airLine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flight flight = (Flight) o;

        return !(flightId != null ? !flightId.equals(flight.flightId) : flight.flightId != null);

    }

    @Override
    public int hashCode() {
        return flightId != null ? flightId.hashCode() : 0;
    }
}
