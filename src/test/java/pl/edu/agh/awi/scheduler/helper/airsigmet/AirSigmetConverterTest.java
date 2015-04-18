package pl.edu.agh.awi.scheduler.helper.airsigmet;

import org.junit.Test;
import pl.edu.agh.awi.downloader.weather.airsigmet.generated.AIRSIGMET;
import pl.edu.agh.awi.downloader.weather.airsigmet.generated.Altitude;
import pl.edu.agh.awi.downloader.weather.airsigmet.generated.Hazard;
import pl.edu.agh.awi.persistence.model.weather_condition.AirSigmet;
import pl.edu.agh.awi.persistence.model.weather_condition.AirSigmetType;
import pl.edu.agh.awi.scheduler.converter.AirSigmetConverter;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import static org.junit.Assert.*;

public class AirSigmetConverterTest {


    public static final int MAX_ALT = 30000;
    public static final int MIN_ALT = 10000;
    public static final String HAZARD_SEVERITY = "MOD";
    public static final String HAZARD_TYPE = "TURB";
    public static final String AIRSIGMET_TYPE = "AIRMET";
    public static final int MOVEMENT_DEG = 120;
    public static final int MOVEMENT_SPEED = 12;
    public static final double DELTA = .1;

    @Test
    public void singleConversion() {
        AIRSIGMET airsigmet = prepare();

        AirSigmet converted = AirSigmetConverter.convert(airsigmet);

        assertNotNull(converted);
        assertEquals(converted.getType(), AirSigmetType.AIRMET);
        assertEquals(converted.getMaxAltitude(), MAX_ALT, DELTA);
        assertEquals(converted.getMinAltitude(), MIN_ALT, DELTA);
        assertEquals(converted.getMovementDirection(), String.valueOf(MOVEMENT_DEG));
        assertEquals(converted.getMovementSpeed(), MOVEMENT_SPEED, DELTA);
        assertEquals(converted.getValidFrom(), getDate(2015, 4, 12, 15, 45, 0));
        assertEquals(converted.getValidTo(), getDate(2015, 4, 12, 16, 15, 0));
    }

    private Date getDate(int year, int month, int day, int hour, int min, int sec) {
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour, min, sec);
        return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
    }

    private AIRSIGMET prepare() {
        Altitude altitude = new Altitude();
        altitude.setMaxFtMsl(MAX_ALT);
        altitude.setMinFtMsl(MIN_ALT);

        Hazard hazard = new Hazard();
        hazard.setSeverity(HAZARD_SEVERITY);
        hazard.setType(HAZARD_TYPE);

        AIRSIGMET airsigmet = new AIRSIGMET();
        airsigmet.setAirsigmetType(AIRSIGMET_TYPE);
        airsigmet.setAltitude(altitude);
        airsigmet.setHazard(hazard);
        airsigmet.setMovementDirDegrees(BigInteger.valueOf(MOVEMENT_DEG));
        airsigmet.setMovementSpeedKt(BigInteger.valueOf(MOVEMENT_SPEED));
        airsigmet.setValidTimeFrom("2015-04-12T15:45:00Z");
        airsigmet.setValidTimeTo("2015-04-12T16:15:00Z");

        return airsigmet;
    }

}