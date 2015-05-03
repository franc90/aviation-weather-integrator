package pl.edu.agh.awi.api.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.awi.api.model.AirLineAPIObject;
import pl.edu.agh.awi.persistence.model.AirLine;

import java.util.Optional;

@Component
public class AirLineAPIConverter extends AbstractConverter<AirLine, AirLineAPIObject> {

    @Autowired
    private FlightAPIConverter flightAPIConverter;

    @Override
    public AirLineAPIObject deepConvert(Optional<AirLine> source) {
        if (source.isPresent()) {
            AirLineAPIObject converted = getConverted(source.get());
            converted.setFlights(flightAPIConverter.convert(source.get().getFlights()));
            return converted;
        }
        return null;
    }

    @Override
    public AirLineAPIObject getConverted(AirLine source) {
        AirLineAPIObject airline = new AirLineAPIObject();
        airline.setName(source.getName());
        airline.setIcaoCode(source.getIcaoCode());
        airline.setIataCode(source.getIataCode());

        return airline;
    }
}
