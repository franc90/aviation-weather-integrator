package pl.edu.agh.awi.api.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.awi.api.model.AirSigmetAPIObject;
import pl.edu.agh.awi.persistence.model.weather_condition.AirSigmet;

@Component
public class AirSigmetAPIConverter extends AbstractConverter<AirSigmet, AirSigmetAPIObject> {

    @Autowired
    private HazardAPIConverter hazardAPIConverter;

    @Override
    public AirSigmetAPIObject convert(AirSigmet source, boolean deep) {
        if (source == null) {
            return null;
        }

        AirSigmetAPIObject target = new AirSigmetAPIObject();
        target.setMaxAltitude(source.getMaxAltitude());
        target.setMinAltitude(source.getMinAltitude());
        target.setMovementDirection(source.getMovementDirection());
        target.setMovementSpeed(source.getMovementSpeed());
        target.setTimestamp(source.getTimestamp());
        target.setValidFrom(source.getValidFrom());
        target.setValidTo(source.getValidTo());

        if (source.getType() != null) {
            target.setType(source.getType().name());
        }

        if (deep) {
            target.setHazard(hazardAPIConverter.convert(source.getHazard(), deep));
        }
        return target;
    }
}
