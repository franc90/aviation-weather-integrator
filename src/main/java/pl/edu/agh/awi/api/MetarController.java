package pl.edu.agh.awi.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.awi.api.converter.MetarsAPIConverter;
import pl.edu.agh.awi.api.model.MetarAPIObject;
import pl.edu.agh.awi.persistence.PersistenceService;
import pl.edu.agh.awi.persistence.model.weather_condition.Metar;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/metar")
public class MetarController {

    @Autowired
    private PersistenceService persistenceService;

    @Autowired
    private MetarsAPIConverter metarsAPIConverter;

    @RequestMapping(value = "{iata}", method = RequestMethod.GET)
    public Set<MetarAPIObject> get(@PathVariable(value = "iata") String iata) {
        Set<Metar> persistedMetars = new HashSet<>(persistenceService.findMetarByAirportIata(iata));
        Set<MetarAPIObject> convert = metarsAPIConverter.convert(persistedMetars, true);
        return convert;
    }

}
