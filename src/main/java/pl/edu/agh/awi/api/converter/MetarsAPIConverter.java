package pl.edu.agh.awi.api.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.awi.api.model.MetarAPIObject;
import pl.edu.agh.awi.persistence.model.weather_condition.Metar;

import java.util.Optional;

@Component
public class MetarsAPIConverter extends AbstractConverter<Metar, MetarAPIObject> {

    @Autowired
    private WeatherAPIConverter weatherAPIConverter;

    @Override
    public MetarAPIObject deepConvert(Optional<Metar> source) {
        if (source.isPresent()) {
            MetarAPIObject converted = getConverted(source.get());
            weatherAPIConverter.deepConvert(converted, source.get());
            return converted;
        }
        return null;
    }

    @Override
    public MetarAPIObject convert(Optional<Metar> source) {
        if (source.isPresent()) {
            MetarAPIObject converted = getConverted(source.get());
            weatherAPIConverter.convert(converted, source.get());
            return converted;
        }
        return null;
    }

    @Override
    public MetarAPIObject getConverted(Metar source) {
        MetarAPIObject target = new MetarAPIObject();

        target.setTimestamp(source.getTimestamp());
        return target;
    }
}
