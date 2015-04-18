package pl.edu.agh.awi.scheduled.helper.airsigmet;

import pl.edu.agh.awi.downloader.weather.airsigmet.generated.AIRSIGMET;
import pl.edu.agh.awi.downloader.weather.airsigmet.generated.Altitude;
import pl.edu.agh.awi.persistence.model.weather_condition.AirSigmet;
import pl.edu.agh.awi.persistence.model.weather_condition.AirSigmetType;
import pl.edu.agh.awi.scheduled.helper.DateHelper;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AirSigmetConverter {

    private static final String AIRMET = "airmet";

    public static AirSigmet convert(AIRSIGMET source) {
        AirSigmet airsigmet = new AirSigmet();

        airsigmet.setHazard(HazardConverter.convert(source.getHazard()));

        if (source.getAltitude() != null) {
            Altitude altitude = source.getAltitude();
            airsigmet.setMinAltitude(Double.valueOf(altitude.getMinFtMsl()));
            airsigmet.setMaxAltitude(Double.valueOf(altitude.getMaxFtMsl()));
        }

        airsigmet.setMovementDirection(String.valueOf(source.getMovementDirDegrees()));
        BigInteger movementSpeedKt = source.getMovementSpeedKt();
        if (movementSpeedKt != null) {
            airsigmet.setMovementSpeed(movementSpeedKt.doubleValue());
        }

        if (AIRMET.equalsIgnoreCase(source.getAirsigmetType())) {
            airsigmet.setType(AirSigmetType.AIRMET);
        } else {
            airsigmet.setType(AirSigmetType.SIGMET);
        }

        airsigmet.setValidFrom(DateHelper.getDate(source.getValidTimeFrom()));
        airsigmet.setValidTo(DateHelper.getDate(source.getValidTimeTo()));

        return airsigmet;
    }


}
