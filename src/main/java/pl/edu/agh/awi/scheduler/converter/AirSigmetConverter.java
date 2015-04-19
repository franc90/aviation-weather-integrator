package pl.edu.agh.awi.scheduler.converter;

import pl.edu.agh.awi.downloader.weather.airsigmet.generated.AIRSIGMET;
import pl.edu.agh.awi.downloader.weather.airsigmet.generated.Altitude;
import pl.edu.agh.awi.persistence.model.weather_condition.AirSigmet;
import pl.edu.agh.awi.persistence.model.weather_condition.AirSigmetType;
import pl.edu.agh.awi.scheduler.helper.DateHelper;

import java.util.Optional;

public class AirSigmetConverter {

    private static final String AIRMET = "airmet";

    public static AirSigmet convert(AIRSIGMET source) {
        AirSigmet airsigmet = new AirSigmet();

        airsigmet.setValidFrom(DateHelper.getDate(source.getValidTimeFrom()));
        airsigmet.setValidTo(DateHelper.getDate(source.getValidTimeTo()));
        airsigmet.setHazard(HazardConverter.convert(source.getHazard()));

        Optional<AIRSIGMET> optional = Optional.ofNullable(source);

        optional.flatMap(o -> Optional.ofNullable(o.getAltitude()))
                .flatMap(o -> Optional.ofNullable(o.getMinFtMsl()))
                .ifPresent(val -> airsigmet.setMinAltitude(val.doubleValue()));

        optional.flatMap(o -> Optional.ofNullable(o.getAltitude()))
                .flatMap(o -> Optional.ofNullable(o.getMaxFtMsl()))
                .ifPresent(val -> airsigmet.setMaxAltitude(val.doubleValue()));

        optional.flatMap(o -> Optional.ofNullable(o.getMovementDirDegrees()))
                .ifPresent(val -> airsigmet.setMovementDirection(val.toString()));

        optional.flatMap(o -> Optional.ofNullable(o.getMovementSpeedKt()))
                .ifPresent(val -> airsigmet.setMovementSpeed(val.doubleValue()));


        if (AIRMET.equalsIgnoreCase(source.getAirsigmetType())) {
            airsigmet.setType(AirSigmetType.AIRMET);
        } else {
            airsigmet.setType(AirSigmetType.SIGMET);
        }

        return airsigmet;
    }

}
