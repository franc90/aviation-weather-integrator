package pl.edu.agh.awi.api.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.awi.api.model.AirPortAPIObject;
import pl.edu.agh.awi.persistence.model.AirPort;
import pl.edu.agh.awi.persistence.model.DestinationAirPort;

@Component
public class DestinationAirPortAPIConverter extends AbstractConverter<DestinationAirPort, AirPortAPIObject> {

    @Autowired
    private AirPortAPIConverter converter;

    @Override
    public AirPortAPIObject convert(DestinationAirPort source, boolean deep) {
        AirPort arrivalAirPort = source.getArrivalAirPort();
        AirPortAPIObject airport = converter.convert(arrivalAirPort, deep);
        return airport;
    }
}
