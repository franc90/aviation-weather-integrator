package pl.edu.agh.awi.loader.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.awi.downloader.flights.airport.data.Airport;
import pl.edu.agh.awi.loader.file_loader.FileLoader;
import pl.edu.agh.awi.persistence.model.AirPort;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AirportConverter implements LoaderConverter<Airport, AirPort> {

    @Autowired
    private FileLoader<Long> runwaysLoader;

    @Autowired
    private FileLoader<String> cityLoader;

    @Override
    public List<AirPort> convert(List<Airport> list) {
        List<AirPort> airPorts = list.stream().map(this::apply).collect(Collectors.<AirPort>toList());
        runwaysLoader.clear();
        cityLoader.clear();

        return airPorts;
    }

    @Override
    public AirPort apply(Airport airport) {
        AirPort airPort = new AirPort();
        airPort.setName(airport.getName());
        airPort.setIataCode(airport.getIata());
        airPort.setIcaoCode(airport.getIcao());
        airPort.setCountry(airport.getCountry());
        airPort.setLatitude((double) airport.getLatitude());
        airPort.setLongitude((double) airport.getLongitude());
        airPort.setCity(cityLoader.getValue(airport.getIcao()));
        airPort.setNumberOfRunways(runwaysLoader.getValue(airport.getIcao()).intValue());

        return airPort;
    }
}
