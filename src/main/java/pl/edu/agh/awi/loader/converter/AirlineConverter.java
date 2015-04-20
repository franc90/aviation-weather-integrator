package pl.edu.agh.awi.loader.converter;

import org.springframework.stereotype.Component;
import pl.edu.agh.awi.downloader.flights.airline.data.Airline;
import pl.edu.agh.awi.persistence.model.AirLine;

@Component
public class AirlineConverter implements LoaderConverter<Airline, AirLine> {

    @Override
    public AirLine apply(Airline airline) {
        AirLine airLine = new AirLine();
        airLine.setName(airline.getName());
        airLine.setIataCode(airline.getIata());
        airLine.setIcaoCode(airline.getIcao());
        return airLine;
    }

}
