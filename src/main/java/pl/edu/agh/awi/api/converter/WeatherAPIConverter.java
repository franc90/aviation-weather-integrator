package pl.edu.agh.awi.api.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.awi.api.model.AbstractWeatherAPIObject;
import pl.edu.agh.awi.persistence.model.weather_condition.AbstractWeatherCondition;

@Component
public class WeatherAPIConverter {

    @Autowired
    private IcingAPIConverter icingAPIConverter;

    @Autowired
    private SkyAPIConverter skyAPIConverter;

    @Autowired
    private TurbulenceAPIConverter turbulenceAPIConverter;


    public AbstractWeatherAPIObject convert(AbstractWeatherAPIObject target, AbstractWeatherCondition source) {
        target.setDewPointTemperature(source.getDewPointTemperature());
        target.setInfoType(source.getInfoType());
        target.setPercipation(source.getPercipation());
        target.setPressure(source.getPressure());
        target.setTemperature(source.getTemperature());
        target.setVisibilityStatute(source.getVisibilityStatute());
        target.setWindDirection(source.getWindDirection());
        target.setWindGust(source.getWindGust());
        target.setWindSpeed(source.getWindSpeed());

        return target;
    }

    public AbstractWeatherAPIObject deepConvert(AbstractWeatherAPIObject target, AbstractWeatherCondition source) {
        convert(target, source);

        target.setIcingConditions(icingAPIConverter.convert(source.getIcingConditions()));
        target.setSkyConditions(skyAPIConverter.convert(source.getSkyConditions()));
        target.setTurbulenceConditions(turbulenceAPIConverter.convert(source.getTurbulenceConditions()));

        return target;
    }

}
