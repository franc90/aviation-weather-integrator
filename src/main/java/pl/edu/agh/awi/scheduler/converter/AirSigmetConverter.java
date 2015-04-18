package pl.edu.agh.awi.scheduler.converter;

import pl.edu.agh.awi.downloader.weather.airsigmet.generated.AIRSIGMET;
import pl.edu.agh.awi.downloader.weather.airsigmet.generated.Altitude;
import pl.edu.agh.awi.persistence.model.weather_condition.AirSigmet;
import pl.edu.agh.awi.persistence.model.weather_condition.AirSigmetType;
import pl.edu.agh.awi.scheduler.helper.DateHelper;

public class AirSigmetConverter {

    private static final String AIRMET = "airmet";

    public static AirSigmet convert(AIRSIGMET source) {
        AirSigmet airsigmet = new AirSigmet();

        airsigmet.setValidFrom(DateHelper.getDate(source.getValidTimeFrom()));
        airsigmet.setValidTo(DateHelper.getDate(source.getValidTimeTo()));
        airsigmet.setHazard(HazardConverter.convert(source.getHazard()));

        if (source.getAltitude() != null) {
            Altitude altitude = source.getAltitude();
            if (altitude.getMinFtMsl() != null) {
                airsigmet.setMinAltitude(altitude.getMinFtMsl().doubleValue());
            }
            if (altitude.getMaxFtMsl() != null) {
                airsigmet.setMaxAltitude(altitude.getMaxFtMsl().doubleValue());
            }
        }

        if (source.getMovementDirDegrees() != null) {
            airsigmet.setMovementDirection(String.valueOf(source.getMovementDirDegrees()));
        }

        if (source.getMovementSpeedKt() != null) {
            airsigmet.setMovementSpeed(source.getMovementSpeedKt().doubleValue());
        }

        if (AIRMET.equalsIgnoreCase(source.getAirsigmetType())) {
            airsigmet.setType(AirSigmetType.AIRMET);
        } else {
            airsigmet.setType(AirSigmetType.SIGMET);
        }

        return airsigmet;
    }


}
