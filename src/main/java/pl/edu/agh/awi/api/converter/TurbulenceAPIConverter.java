package pl.edu.agh.awi.api.converter;

import org.springframework.stereotype.Component;
import pl.edu.agh.awi.api.model.TurbulenceConditionAPIObject;
import pl.edu.agh.awi.persistence.model.weather_condition.TurbulenceCondition;

@Component
public class TurbulenceAPIConverter extends AbstractConverter<TurbulenceCondition, TurbulenceConditionAPIObject> {

    @Override
    public TurbulenceConditionAPIObject convert(TurbulenceCondition source, boolean deep) {
        if (source == null) {
            return null;
        }

        TurbulenceConditionAPIObject target = new TurbulenceConditionAPIObject();
        target.setTurbulenceIntensity(source.getTurbulenceIntensity());
        target.setTurbulenceMaxAltitude(source.getTurbulenceMaxAltitude());
        target.setTurbulenceMinAltitude(source.getTurbulenceMinAltitude());

        return target;
    }
}
