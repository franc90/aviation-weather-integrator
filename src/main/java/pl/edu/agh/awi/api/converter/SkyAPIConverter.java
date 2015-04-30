package pl.edu.agh.awi.api.converter;

import org.springframework.stereotype.Component;
import pl.edu.agh.awi.api.model.SkyConditionAPIObject;
import pl.edu.agh.awi.persistence.model.weather_condition.SkyCondition;

@Component
public class SkyAPIConverter extends AbstractConverter<SkyCondition, SkyConditionAPIObject> {
    @Override
    public SkyConditionAPIObject convert(SkyCondition source, boolean deep) {
        if (source == null) {
            return null;
        }

        SkyConditionAPIObject target = new SkyConditionAPIObject();

        target.setCloudBase(source.getCloudBase());
        target.setSkyCover(source.getSkyCover());

        return target;
    }
}
