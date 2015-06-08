package pl.edu.agh.awi.api.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.awi.api.model.AirPortAPIObject;
import pl.edu.agh.awi.persistence.model.AirPort;
import pl.edu.agh.awi.persistence.model.DestinationAirPort;

import java.util.Optional;

@Component
public class DestinationAirPortAPIConverter extends AbstractConverter<DestinationAirPort, AirPortAPIObject> {

    @Autowired
    private AirPortAPIConverter converter;

    @Override
    public AirPortAPIObject deepConvert(Optional<DestinationAirPort> source) {
        AirPort arrivalAirPort = source.get().getArrivalAirPort();
        return converter.deepConvert(Optional.ofNullable(arrivalAirPort));
    }

    @Override
    public AirPortAPIObject getConverted(DestinationAirPort source) {
        AirPort arrivalAirPort = source.getArrivalAirPort();
        AirPortAPIObject airport = converter.convert(Optional.ofNullable(arrivalAirPort));
        return airport;
    }
}
