package pl.edu.agh.awi.api.converter;

import org.springframework.stereotype.Component;
import pl.edu.agh.awi.api.model.HazardAPIObject;
import pl.edu.agh.awi.persistence.model.weather_condition.Hazard;

@Component
public class HazardAPIConverter extends AbstractConverter<Hazard, HazardAPIObject> {

    @Override
    public HazardAPIObject convert(Hazard source, boolean deep) {

        if (source == null) {
            return null;
        }

        HazardAPIObject target = new HazardAPIObject();
        target.setSeverity(source.getSeverity());
        target.setType(source.getType());

        return target;
    }
}
