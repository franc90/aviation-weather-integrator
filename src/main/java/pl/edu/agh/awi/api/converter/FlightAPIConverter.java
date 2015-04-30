package pl.edu.agh.awi.api.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.awi.api.model.FlightAPIObject;
import pl.edu.agh.awi.persistence.model.Flight;

@Component
public class FlightAPIConverter extends AbstractConverter<Flight, FlightAPIObject> {

    @Autowired
    private AirPortAPIConverter airPortAPIConverter;
    @Autowired
    private AirLineAPIConverter airLineAPIConverter;
    @Autowired
    private DestinationAirPortAPIConverter destinationAirPortConverter;

    @Override
    public FlightAPIObject convert(Flight source, boolean deep) {
        if (source == null) {
            return null;
        }

        FlightAPIObject flight = new FlightAPIObject();

        flight.setFlightId(source.getFlightId());
        flight.setActualArrivalTime(source.getActualArrivalTime());
        flight.setActualDepartureTime(source.getActualDepartureTime());
        flight.setScheduledArrivalTime(source.getScheduledArrivalTime());
        flight.setScheduledDepartureTime(source.getScheduledDepartureTime());
        flight.setAircraft(source.getAircraft());
        flight.setStatus(source.getStatus());
        flight.setAirLine(airLineAPIConverter.convert(source.getAirLine(), false));
        flight.setDepartureAirport(airPortAPIConverter.convert(source.getDepartureAirport(), false));

        if (deep) {
            flight.setArrivalAirports(destinationAirPortConverter.convert(source.getArrivalAirports(), false));
        }

        return flight;
    }


}
