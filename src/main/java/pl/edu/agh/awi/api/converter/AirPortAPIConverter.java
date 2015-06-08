package pl.edu.agh.awi.api.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.awi.api.model.AirPortAPIObject;
import pl.edu.agh.awi.persistence.model.AirPort;

import java.util.Optional;

@Component
public class AirPortAPIConverter extends AbstractConverter<AirPort, AirPortAPIObject> {

    @Autowired
    private FlightAPIConverter flightAPIConverter;

    @Autowired
    private AirSigmetAPIConverter airSigmetAPIConverter;

    @Autowired
    private MetarsAPIConverter metarsAPIConverter;

    @Autowired
    private TafsAPIConverter tafsAPIConverter;

    @Override
    public AirPortAPIObject deepConvert(Optional<AirPort> source) {
        if (source.isPresent()) {
            AirPortAPIObject converted = getConverted(source.get());
            AirPort airPort = source.get();

            converted.setAirSigmets(airSigmetAPIConverter.convert(airPort.getAirSigmets()));
            converted.setMetars(metarsAPIConverter.convert(airPort.getMetars()));
            converted.setTafs(tafsAPIConverter.convert(airPort.getTafs()));
            converted.setIncomingFlights(flightAPIConverter.convert(airPort.getIncomingFlights()));
            converted.setOutgoingFlights(flightAPIConverter.convert(airPort.getOutgoingFlights()));

            return converted;
        }
        return null;
    }

    @Override
    public AirPortAPIObject getConverted(AirPort source) {
        AirPortAPIObject airport = new AirPortAPIObject();

        airport.setName(source.getName());
        airport.setIataCode(source.getIataCode());
        airport.setIcaoCode(source.getIcaoCode());
        airport.setCity(source.getCity());
        airport.setCountry(source.getCountry());
        airport.setLatitude(source.getLatitude());
        airport.setLongitude(source.getLongitude());
        airport.setNumberOfRunways(source.getNumberOfRunways());


        return airport;
    }
}
