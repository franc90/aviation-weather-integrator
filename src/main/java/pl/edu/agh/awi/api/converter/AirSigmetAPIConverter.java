package pl.edu.agh.awi.api.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.awi.api.model.AirSigmetAPIObject;
import pl.edu.agh.awi.persistence.model.weather_condition.AirSigmet;

import javax.swing.text.html.Option;
import java.util.Optional;

@Component
public class AirSigmetAPIConverter extends AbstractConverter<AirSigmet, AirSigmetAPIObject> {

    @Autowired
    private HazardAPIConverter hazardAPIConverter;

    @Override
    public AirSigmetAPIObject deepConvert(Optional<AirSigmet> source) {
        if (source.isPresent()) {
            AirSigmetAPIObject converted = getConverted(source.get());
            converted.setHazard(hazardAPIConverter.deepConvert(Optional.ofNullable(source.get().getHazard())));
            return converted;
        }
        return null;
    }

    @Override
    public AirSigmetAPIObject getConverted(AirSigmet source) {
        AirSigmetAPIObject target = new AirSigmetAPIObject();

        target.setMaxAltitude(source.getMaxAltitude());
        target.setMinAltitude(source.getMinAltitude());
        target.setMovementDirection(source.getMovementDirection());
        target.setMovementSpeed(source.getMovementSpeed());
        target.setTimestamp(source.getTimestamp());
        target.setValidFrom(source.getValidFrom());
        target.setValidTo(source.getValidTo());

        Optional
                .ofNullable(source.getType())
                .flatMap(o -> Optional.ofNullable(o.name()))
                .ifPresent(target::setType);

        return target;
    }
}
