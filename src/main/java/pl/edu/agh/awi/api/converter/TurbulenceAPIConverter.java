package pl.edu.agh.awi.api.converter;

import org.springframework.stereotype.Component;
import pl.edu.agh.awi.api.model.TurbulenceConditionAPIObject;
import pl.edu.agh.awi.persistence.model.weather_condition.TurbulenceCondition;

import java.util.Optional;

@Component
public class TurbulenceAPIConverter extends AbstractConverter<TurbulenceCondition, TurbulenceConditionAPIObject> {

    @Override
    public TurbulenceConditionAPIObject deepConvert(Optional<TurbulenceCondition> source) {
        return convert(source);
    }

    @Override
    public TurbulenceConditionAPIObject getConverted(TurbulenceCondition source) {
        TurbulenceConditionAPIObject target = new TurbulenceConditionAPIObject();
        target.setTurbulenceIntensity(source.getTurbulenceIntensity());
        target.setTurbulenceMaxAltitude(source.getTurbulenceMaxAltitude());
        target.setTurbulenceMinAltitude(source.getTurbulenceMinAltitude());

        return target;
    }
}
