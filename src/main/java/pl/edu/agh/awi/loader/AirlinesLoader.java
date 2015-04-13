package pl.edu.agh.awi.loader;

import org.springframework.stereotype.Component;
import pl.edu.agh.awi.downloader.flights.airline.data.Airline;
import pl.edu.agh.awi.downloader.flights.airline.data.AirlineResponse;
import pl.edu.agh.awi.persistence.model.AirLine;

import java.util.List;

@Component
public class AirlinesLoader extends AbstractLoader<AirlineResponse, Airline, AirLine> {

    @Override
    public void loadData() {
        AirlineResponse response = client.getResponse();
        List<AirLine> airLines = converter.convert(response.getAirlines());

        // TODO: save to db
        System.out.println("AIRLINES_LOADER: " + airLines.size());
    }
}
