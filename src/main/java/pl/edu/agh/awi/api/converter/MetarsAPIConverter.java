package pl.edu.agh.awi.api.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.awi.api.model.MetarAPIObject;
import pl.edu.agh.awi.persistence.model.weather_condition.Metar;

@Component
public class MetarsAPIConverter extends AbstractConverter<Metar, MetarAPIObject> {

    @Autowired
    private WeatherAPIConverter weatherAPIConverter;

    @Override
    public MetarAPIObject convert(Metar source, boolean deep) {
        if (source == null) {
            return null;
        }

        MetarAPIObject target = new MetarAPIObject();
        weatherAPIConverter.convert(target, source, deep);
        target.setTimestamp(source.getTimestamp());

        return target;
    }
}
