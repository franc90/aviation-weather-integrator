package pl.edu.agh.awi.api.converter;

import org.springframework.stereotype.Component;
import pl.edu.agh.awi.api.model.SkyConditionAPIObject;
import pl.edu.agh.awi.persistence.model.weather_condition.SkyCondition;

import java.util.Optional;

@Component
public class SkyAPIConverter extends AbstractConverter<SkyCondition, SkyConditionAPIObject> {

    @Override
    public SkyConditionAPIObject deepConvert(Optional<SkyCondition> source) {
        return convert(source);
    }

    @Override
    public SkyConditionAPIObject getConverted(SkyCondition source) {
        SkyConditionAPIObject target = new SkyConditionAPIObject();

        target.setCloudBase(source.getCloudBase());
        target.setSkyCover(source.getSkyCover());

        return target;
    }
}
