package pl.edu.agh.awi.api.converter;

import org.springframework.stereotype.Component;
import pl.edu.agh.awi.api.model.HazardAPIObject;
import pl.edu.agh.awi.persistence.model.weather_condition.Hazard;

import java.util.Optional;

@Component
public class HazardAPIConverter extends AbstractConverter<Hazard, HazardAPIObject> {

    @Override
    public HazardAPIObject deepConvert(Optional<Hazard> source) {
        return convert(source);
    }

    @Override
    public HazardAPIObject getConverted(Hazard source) {
        HazardAPIObject target = new HazardAPIObject();
        target.setSeverity(source.getSeverity());
        target.setType(source.getType());

        return target;
    }
}
