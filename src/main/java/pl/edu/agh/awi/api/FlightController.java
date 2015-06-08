package pl.edu.agh.awi.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.awi.api.converter.AirLineAPIConverter;
import pl.edu.agh.awi.api.converter.AirPortAPIConverter;
import pl.edu.agh.awi.api.converter.DestinationAirPortAPIConverter;
import pl.edu.agh.awi.api.converter.FlightAPIConverter;
import pl.edu.agh.awi.api.model.AirLineAPIObject;
import pl.edu.agh.awi.api.model.AirPortAPIObject;
import pl.edu.agh.awi.api.model.FlightAPIObject;
import pl.edu.agh.awi.persistence.PersistenceService;
import pl.edu.agh.awi.persistence.model.AirLine;
import pl.edu.agh.awi.persistence.model.AirPort;
import pl.edu.agh.awi.persistence.model.DestinationAirPort;
import pl.edu.agh.awi.persistence.model.Flight;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/flight")
public class FlightController {

    @Autowired
    private PersistenceService persistenceService;

    @Autowired
    private FlightAPIConverter flightAPIConverter;

    @Autowired
    private AirLineAPIConverter airLineAPIConverter;

    @Autowired
    private AirPortAPIConverter airPortAPIConverter;

    @Autowired
    private DestinationAirPortAPIConverter destinationAirPortAPIConverter;

    @RequestMapping(value = "{flightId}", method = RequestMethod.GET)
    public FlightAPIObject get(@PathVariable(value = "flightId") String flightId) {
        Flight flightByFlightId = persistenceService.findFlightByFlightId(flightId);
        FlightAPIObject flight = flightAPIConverter.deepConvert(Optional.ofNullable(flightByFlightId));
        return flight;
    }

    @RequestMapping(value = "{flightId}/airline", method = RequestMethod.GET)
    public AirLineAPIObject getAirline(@PathVariable(value = "flightId") String flightId) {
        Flight flightByFlightId = persistenceService.findFlightByFlightId(flightId);
        AirLine airLine = flightByFlightId.getAirLine();
        AirLineAPIObject convert = airLineAPIConverter.deepConvert(Optional.ofNullable(airLine));
        return convert;
    }

    @RequestMapping(value = "{flightId}/dep", method = RequestMethod.GET)
    public AirPortAPIObject getDepartureAirport(@PathVariable(value = "flightId") String flightId) {
        Flight flightByFlightId = persistenceService.findFlightByFlightId(flightId);
        AirPort departureAirport = flightByFlightId.getDepartureAirport();
        AirPortAPIObject convert = airPortAPIConverter.convert(Optional.ofNullable(departureAirport));
        return convert;
    }

    @RequestMapping(value = "{flightId}/arr", method = RequestMethod.GET)
    public Set<AirPortAPIObject> getArrivalAirport(@PathVariable(value = "flightId") String flightId) {
        Flight flightByFlightId = persistenceService.findFlightByFlightId(flightId);
        Set<DestinationAirPort> arrivalAirports = flightByFlightId.getArrivalAirports();
        Set<AirPortAPIObject> convert = destinationAirPortAPIConverter.convert(arrivalAirports);
        return convert;
    }

}
