package pl.edu.agh.awi.api.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.awi.api.model.TafAPIObject;
import pl.edu.agh.awi.persistence.model.weather_condition.Taf;

@Component
public class TafsAPIConverter extends AbstractConverter<Taf, TafAPIObject> {

    @Autowired
    private WeatherAPIConverter weatherAPIConverter;

    @Override
    public TafAPIObject convert(Taf source, boolean deep) {
        if (source == null) {
            return null;
        }

        TafAPIObject target = new TafAPIObject();
        weatherAPIConverter.convert(target, source, deep);
        target.setValidFrom(source.getValidFrom());
        target.setValidTo(source.getValidTo());

        return target;
    }
}
