package pl.edu.agh.awi.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.awi.api.converter.TafsAPIConverter;
import pl.edu.agh.awi.api.model.TafAPIObject;
import pl.edu.agh.awi.persistence.PersistenceService;
import pl.edu.agh.awi.persistence.model.weather_condition.Taf;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/taf")
public class TafController {

    @Autowired
    private PersistenceService persistenceService;

    @Autowired
    private TafsAPIConverter tafsAPIConverter;

    @RequestMapping(value = "{iata}", method = RequestMethod.GET)
    public Set<TafAPIObject> get(@PathVariable(value = "iata") String iata) {
        Set<Taf> persistedMetars = new HashSet<>(persistenceService.findTafByAirportIata(iata));
        Set<TafAPIObject> convert = tafsAPIConverter.convert(persistedMetars, true);
        return convert;
    }

}
