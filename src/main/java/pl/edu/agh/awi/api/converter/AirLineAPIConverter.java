package pl.edu.agh.awi.api.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.awi.api.model.AirLineAPIObject;
import pl.edu.agh.awi.persistence.model.AirLine;

@Component
public class AirLineAPIConverter extends AbstractConverter<AirLine, AirLineAPIObject> {

    @Autowired
    private FlightAPIConverter flightAPIConverter;

    @Override
    public AirLineAPIObject convert(AirLine source, boolean deep) {
        if (source == null) {
            return null;
        }

        AirLineAPIObject airline = new AirLineAPIObject();
        airline.setName(source.getName());
        airline.setIcaoCode(source.getIcaoCode());
        airline.setIataCode(source.getIataCode());
        if (deep) {
            airline.setFlights(flightAPIConverter.convert(source.getFlights(), false));
        }

        return airline;
    }
}
