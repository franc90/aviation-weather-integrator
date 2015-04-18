package pl.edu.agh.awi.persistence.model;

import org.springframework.data.neo4j.annotation.*;

@RelationshipEntity(type = "to")
public class DestinationAirPort {

    @GraphId
    private Long id;

    private Long ordinalNumber;

    @StartNode
    @Fetch
    private Flight flight;

    @EndNode
    @Fetch
    private AirPort arrivalAirPort;

    public DestinationAirPort() {

    }

    private DestinationAirPort(Long ordinalNumber, Flight flight, AirPort arrivalAirPort) {
        this.ordinalNumber = ordinalNumber;
        this.flight = flight;
        this.arrivalAirPort = arrivalAirPort;
    }

    static DestinationAirPortBuilder build() {
        return ordinalNumber -> flight -> airPort -> new DestinationAirPort(ordinalNumber, flight, airPort);
    }

    public Long getOrdinalNumber() {
        return ordinalNumber;
    }

    public Flight getFlight() {
        return flight;
    }

    public AirPort getArrivalAirPort() {
        return arrivalAirPort;
    }

    interface DestinationAirPortBuilder {
        FlightFunction withOrdinalNumber(Long ordinalNumber);
    }

    interface FlightFunction {
        ArrivalAirPortFunction withFlight(Flight flight);
    }

    interface ArrivalAirPortFunction {
        DestinationAirPort withArrivalAirPort(AirPort airPort);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DestinationAirPort that = (DestinationAirPort) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
