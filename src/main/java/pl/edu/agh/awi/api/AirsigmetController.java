package pl.edu.agh.awi.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.awi.api.converter.AirSigmetAPIConverter;
import pl.edu.agh.awi.api.model.AirSigmetAPIObject;
import pl.edu.agh.awi.persistence.PersistenceService;
import pl.edu.agh.awi.persistence.model.weather_condition.AirSigmet;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/airsigmet")
public class AirsigmetController {

    @Autowired
    private PersistenceService persistenceService;

    @Autowired
    private AirSigmetAPIConverter airSigmetAPIConverter;

    @RequestMapping(value = "{iata}", method = RequestMethod.GET)
    public Set<AirSigmetAPIObject> get(@PathVariable(value = "iata") String iata) {
        Set<AirSigmet> persistedMetars = new HashSet<>(persistenceService.findAirSigmetByAirportIata(iata));
        Set<AirSigmetAPIObject> convert = airSigmetAPIConverter.deepConvert(persistedMetars);
        return convert;
    }

}
