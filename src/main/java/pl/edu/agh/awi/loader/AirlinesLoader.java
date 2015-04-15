package pl.edu.agh.awi.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.awi.downloader.flights.airline.data.Airline;
import pl.edu.agh.awi.downloader.flights.airline.data.AirlineResponse;
import pl.edu.agh.awi.persistence.model.AirLine;
import pl.edu.agh.awi.persistence.repositories.AirLineRepository;

import java.util.List;

@Component
public class AirlinesLoader extends AbstractLoader<AirlineResponse, Airline, AirLine> {

    @Autowired
    private AirLineRepository airLineRepository;

    @Override
    public void loadData() {
        AirlineResponse response = client.getResponse();
        List<AirLine> airLines = converter.convert(response.getAirlines());
        airLineRepository.save(airLines);
    }
}
