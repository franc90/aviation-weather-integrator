package pl.edu.agh.awi.loader;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.awi.downloader.flights.airport.data.Airport;
import pl.edu.agh.awi.downloader.flights.airport.data.AirportResponse;
import pl.edu.agh.awi.persistence.model.AirPort;
import pl.edu.agh.awi.persistence.repositories.AirPortRepository;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AirportsLoader extends AbstractLoader<AirportResponse, Airport, AirPort> {

    private final Set<String> supportCountries = Collections.unmodifiableSet(Sets.newHashSet("United States", "Mexico", "Canada"));

    @Autowired
    private AirPortRepository airPortRepository;

    @Override
    public void loadData() {
        AirportResponse response = client.getResponse();
        List<AirPort> airports = converter.convert(response.getAirports());
        List<AirPort> filteredAirPorts = airports.stream()
                .filter(airPort -> supportCountries.contains(airPort.getCountry()))
                .collect(Collectors.toList());
        airPortRepository.saveIfNotExists(filteredAirPorts);
    }
}
