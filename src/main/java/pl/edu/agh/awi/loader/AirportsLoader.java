package pl.edu.agh.awi.loader;

import org.springframework.stereotype.Component;
import pl.edu.agh.awi.downloader.flights.airport.data.Airport;
import pl.edu.agh.awi.downloader.flights.airport.data.AirportResponse;
import pl.edu.agh.awi.persistence.model.AirPort;

import java.util.List;

@Component
public class AirportsLoader extends AbstractLoader<AirportResponse, Airport, AirPort> {

    @Override
    public void loadData() {
        AirportResponse response = client.getResponse();
        List<AirPort> airports = converter.convert(response.getAirports());

        // TODO: save to db
        System.out.println("AIRPORTS_LOADER: " + airports.size());
    }
}
