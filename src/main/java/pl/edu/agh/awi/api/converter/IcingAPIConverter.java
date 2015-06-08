package pl.edu.agh.awi.api.converter;

import org.springframework.stereotype.Component;
import pl.edu.agh.awi.api.model.IcingConditionAPIObject;
import pl.edu.agh.awi.persistence.model.weather_condition.IcingCondition;

import java.util.Optional;

@Component
public class IcingAPIConverter extends AbstractConverter<IcingCondition, IcingConditionAPIObject> {

    @Override
    public IcingConditionAPIObject deepConvert(Optional<IcingCondition> source) {
        return convert(source);
    }

    @Override
    public IcingConditionAPIObject getConverted(IcingCondition source) {
        IcingConditionAPIObject target = new IcingConditionAPIObject();
        target.setIcingIntensity(source.getIcingIntensity());
        target.setIcingMaxAltitude(source.getIcingMaxAltitude());
        target.setIcingMinAltitude(source.getIcingMinAltitude());

        return target;
    }
}
