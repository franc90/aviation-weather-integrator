package pl.edu.agh.awi.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.awi.api.converter.*;
import pl.edu.agh.awi.api.model.*;
import pl.edu.agh.awi.persistence.PersistenceService;
import pl.edu.agh.awi.persistence.model.AirPort;
import pl.edu.agh.awi.persistence.model.Flight;
import pl.edu.agh.awi.persistence.model.weather_condition.AirSigmet;
import pl.edu.agh.awi.persistence.model.weather_condition.Metar;
import pl.edu.agh.awi.persistence.model.weather_condition.Taf;

import java.util.HashSet;
import java.util.Optional;
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

    @Autowired
    private MetarsAPIConverter metarsAPIConverter;

    @Autowired
    private TafsAPIConverter tafsAPIConverter;

    @Autowired
    private AirSigmetAPIConverter airSigmetAPIConverter;


    @RequestMapping(value = "{iata}", method = RequestMethod.GET)
    public AirPortAPIObject get(@PathVariable(value = "iata") String iata) {
        AirPort persistedAirport = persistenceService.findAirPortByIataCode(iata);
        AirPortAPIObject airport = airPortAPIConverter.convert(Optional.ofNullable(persistedAirport));
        return airport;
    }

    @RequestMapping(value = "{iata}/in", method = RequestMethod.GET)
    public Set<FlightAPIObject> getIncomingFlights(@PathVariable(value = "iata") String iata) {
        Set<Flight> persistedFlights = new HashSet<>(persistenceService.findFlightByArrivalAirportIataCode(iata));
        Set<FlightAPIObject> flights = flightAPIConverter.convert(persistedFlights);
        return flights;
    }

    @RequestMapping(value = "{iata}/out", method = RequestMethod.GET)
    public Set<FlightAPIObject> getOutgoingFlights(@PathVariable(value = "iata") String iata) {
        Set<Flight> persistedFlights = new HashSet<>(persistenceService.findFlightByDepartureAirportIataCode(iata));
        Set<FlightAPIObject> flights = flightAPIConverter.convert(persistedFlights);
        return flights;
    }

    @RequestMapping(value = "{iata}/metar", method = RequestMethod.GET)
    public Set<MetarAPIObject> getMetars(@PathVariable(value = "iata") String iata) {
        Set<Metar> persistedMetars = new HashSet<>(persistenceService.findMetarByAirportIata(iata));
        Set<MetarAPIObject> convert = metarsAPIConverter.deepConvert(persistedMetars);
        return convert;
    }

    @RequestMapping(value = "{iata}/taf", method = RequestMethod.GET)
    public Set<TafAPIObject> getTafs(@PathVariable(value = "iata") String iata) {
        Set<Taf> persistedTafs = new HashSet<>(persistenceService.findTafByAirportIata(iata));
        Set<TafAPIObject> convert = tafsAPIConverter.deepConvert(persistedTafs);
        return convert;
    }

    @RequestMapping(value = "{iata}/airsigmet", method = RequestMethod.GET)
    public Set<AirSigmetAPIObject> getAirSigmets(@PathVariable(value = "iata") String iata) {
        Set<AirSigmet> persistedAirSigmets = new HashSet<>(persistenceService.findAirSigmetByAirportIata(iata));
        Set<AirSigmetAPIObject> convert = airSigmetAPIConverter.deepConvert(persistedAirSigmets);
        return convert;
    }


}
