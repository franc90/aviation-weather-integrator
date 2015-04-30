package pl.edu.agh.awi.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.awi.api.converter.AirLineAPIConverter;
import pl.edu.agh.awi.api.converter.FlightAPIConverter;
import pl.edu.agh.awi.api.model.AirLineAPIObject;
import pl.edu.agh.awi.api.model.FlightAPIObject;
import pl.edu.agh.awi.persistence.PersistenceService;
import pl.edu.agh.awi.persistence.model.AirLine;
import pl.edu.agh.awi.persistence.model.Flight;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/airline")
public class AirlineController {

    @Autowired
    private PersistenceService persistenceService;

    @Autowired
    private AirLineAPIConverter airLineAPIConverter;

    @Autowired
    private FlightAPIConverter flightAPIConverter;

    @RequestMapping(value = "{iata}", method = RequestMethod.GET)
    public AirLineAPIObject get(@PathVariable(value = "iata") String iata) {
        AirLine persistedAirline = persistenceService.findAirLineByIataCode(iata);
        AirLineAPIObject airline = airLineAPIConverter.convert(persistedAirline, false);
        return airline;
    }

    @RequestMapping(value = "{iata}/flights", method = RequestMethod.GET)
    public Set<FlightAPIObject> getFlights(@PathVariable(value = "iata") String iata) {
        Set<Flight> persistedFlights = new HashSet<>(persistenceService.findFlightsByAirLineIataCode(iata));
        Set<FlightAPIObject> flights = flightAPIConverter.convert(persistedFlights, false);
        return flights;
    }

}
