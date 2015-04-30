package pl.edu.agh.awi.api.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.awi.api.model.AirPortAPIObject;
import pl.edu.agh.awi.persistence.model.AirPort;

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
    public AirPortAPIObject convert(AirPort soruce, boolean deep) {
        if (soruce == null) {
            return null;
        }

        AirPortAPIObject airport = new AirPortAPIObject();

        airport.setName(soruce.getName());
        airport.setIataCode(soruce.getIataCode());
        airport.setIcaoCode(soruce.getIcaoCode());
        airport.setCity(soruce.getCity());
        airport.setCountry(soruce.getCountry());
        airport.setLatitude(soruce.getLatitude());
        airport.setLongitude(soruce.getLongitude());
        airport.setNumberOfRunways(soruce.getNumberOfRunways());

        if (deep) {
            airport.setAirSigmets(airSigmetAPIConverter.convert(soruce.getAirSigmets(), false));
            airport.setMetars(metarsAPIConverter.convert(soruce.getMetars(), false));
            airport.setTafs(tafsAPIConverter.convert(soruce.getTafs(), false));
            airport.setIncomingFlights(flightAPIConverter.convert(soruce.getIncomingFlights(), false));
            airport.setOutgoingFlights(flightAPIConverter.convert(soruce.getOutgoingFlights(), false));
        }

        return airport;
    }


}
