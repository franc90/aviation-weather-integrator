package pl.edu.agh.awi.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.awi.api.converter.AirPortAPIConverter;
import pl.edu.agh.awi.api.converter.FlightAPIConverter;
import pl.edu.agh.awi.api.model.AirPortAPIObject;
import pl.edu.agh.awi.api.model.FlightAPIObject;
import pl.edu.agh.awi.persistence.PersistenceService;
import pl.edu.agh.awi.persistence.model.AirPort;
import pl.edu.agh.awi.persistence.model.Flight;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/airport")
public class AirportController {

    @Autowired
    private PersistenceService persistenceService;
    @Autowired
    private AirPortAPIConverter airPortAPIConverter;
    @Autowired
    private FlightAPIConverter flightAPIConverter;

    @RequestMapping(value = "{iata}", method = RequestMethod.GET)
    public AirPortAPIObject get(@PathVariable(value = "iata") String iata) {
        AirPort persistedAirport = persistenceService.findAirPortByIataCode(iata);
        AirPortAPIObject airport = airPortAPIConverter.convert(persistedAirport, false);
        return airport;
    }

    @RequestMapping(value = "{iata}/in", method = RequestMethod.GET)
    public Set<FlightAPIObject> getIncomingFlights(@PathVariable(value = "iata") String iata) {
        Set<Flight> persistedFlights = new HashSet<>(persistenceService.findFlightByArrivalAirportIataCode(iata));
        Set<FlightAPIObject> flights = flightAPIConverter.convert(persistedFlights, false);
        return flights;
    }

    @RequestMapping(value = "{iata}/out", method = RequestMethod.GET)
    public Set<FlightAPIObject> getOutgoingFlights(@PathVariable(value = "iata") String iata) {
        Set<Flight> persistedFlights = new HashSet<>(persistenceService.findFlightByDepartureAirportIataCode(iata));
        Set<FlightAPIObject> flights = flightAPIConverter.convert(persistedFlights, false);
        return flights;
    }
}
