package pl.edu.agh.awi.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.awi.downloader.flights.AbstractFlightsClient;
import pl.edu.agh.awi.downloader.flights.airline.data.Airline;
import pl.edu.agh.awi.downloader.flights.airline.data.AirlineResponse;
import pl.edu.agh.awi.loader.converter.LoaderConverter;
import pl.edu.agh.awi.persistence.model.AirLine;

import java.util.List;

@Component
public class AirlinesLoader implements StartupLoader {

    @Autowired
    private AbstractFlightsClient<AirlineResponse, ?> client;

    @Autowired
    private LoaderConverter<Airline, AirLine> converter;

    @Override
    public void loadData() {
        AirlineResponse response = client.getResponse();
        List<AirLine> airLines = converter.convert(response.getAirlines());

        // TODO: save to db
        System.out.println("AIRLINES_LOADER: " + airLines.size());
    }
}
