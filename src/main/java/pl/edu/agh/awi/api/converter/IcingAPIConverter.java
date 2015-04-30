package pl.edu.agh.awi.api.converter;

import org.springframework.stereotype.Component;
import pl.edu.agh.awi.api.model.IcingConditionAPIObject;
import pl.edu.agh.awi.persistence.model.weather_condition.IcingCondition;

@Component
public class IcingAPIConverter extends AbstractConverter<IcingCondition, IcingConditionAPIObject> {
    @Override
    public IcingConditionAPIObject convert(IcingCondition source, boolean deep) {
        if (source == null) {
            return null;
        }

        IcingConditionAPIObject target = new IcingConditionAPIObject();
        target.setIcingIntensity(source.getIcingIntensity());
        target.setIcingMaxAltitude(source.getIcingMaxAltitude());
        target.setIcingMinAltitude(source.getIcingMinAltitude());

        return target;
    }
}
