package pl.edu.agh.awi.api.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.awi.api.model.TafAPIObject;
import pl.edu.agh.awi.persistence.model.weather_condition.Taf;

import java.util.Optional;

@Component
public class TafsAPIConverter extends AbstractConverter<Taf, TafAPIObject> {

    @Autowired
    private WeatherAPIConverter weatherAPIConverter;

    @Override
    public TafAPIObject convert(Optional<Taf> source) {
        if (source.isPresent()) {
            TafAPIObject converted = getConverted(source.get());
            weatherAPIConverter.convert(converted, source.get());
            return converted;
        }

        return null;
    }

    @Override
    public TafAPIObject deepConvert(Optional<Taf> source) {
        if (source.isPresent()) {
            TafAPIObject converted = getConverted(source.get());
            weatherAPIConverter.deepConvert(converted, source.get());
            return converted;
        }

        return null;
    }

    @Override
    public TafAPIObject getConverted(Taf source) {
        TafAPIObject target = new TafAPIObject();
        target.setValidFrom(source.getValidFrom());
        target.setValidTo(source.getValidTo());

        return target;
    }
}
