package pl.edu.agh.awi.api.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.awi.api.model.FlightAPIObject;
import pl.edu.agh.awi.persistence.model.Flight;

import java.util.Optional;

@Component
public class FlightAPIConverter extends AbstractConverter<Flight, FlightAPIObject> {

    @Autowired
    private AirPortAPIConverter airPortAPIConverter;

    @Autowired
    private AirLineAPIConverter airLineAPIConverter;

    @Autowired
    private DestinationAirPortAPIConverter destinationAirPortConverter;

    @Override
    public FlightAPIObject deepConvert(Optional<Flight> source) {
        if (source.isPresent()) {
            FlightAPIObject converted = getConverted(source.get());
            converted.setArrivalAirports(destinationAirPortConverter.convert(source.get().getArrivalAirports()));
            return converted;
        }
        return null;
    }

    @Override
    public FlightAPIObject getConverted(Flight source) {
        FlightAPIObject flight = new FlightAPIObject();

        flight.setFlightId(source.getFlightId());
        flight.setActualArrivalTime(source.getActualArrivalTime());
        flight.setActualDepartureTime(source.getActualDepartureTime());
        flight.setScheduledArrivalTime(source.getScheduledArrivalTime());
        flight.setScheduledDepartureTime(source.getScheduledDepartureTime());
        flight.setAircraft(source.getAircraft());
        flight.setStatus(source.getStatus());
        flight.setAirLine(airLineAPIConverter.convert(Optional.ofNullable(source.getAirLine())));
        flight.setDepartureAirport(airPortAPIConverter.convert(Optional.ofNullable(source.getDepartureAirport())));

        return flight;
    }
}
