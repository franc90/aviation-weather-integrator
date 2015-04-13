package pl.edu.agh.awi.scheduled.helper.airsigmet;

import org.junit.Test;
import pl.edu.agh.awi.downloader.weather.airsigmet.generated.Hazard;

import static org.junit.Assert.*;

public class HazardConverterTest {

    public static final String SEVERITY = "SEVERITY";
    public static final String TYPE = "TYPE";

    @Test
    public void singleConversion() {
        Hazard hazard = new Hazard();
        hazard.setSeverity(SEVERITY);
        hazard.setType(TYPE);

        pl.edu.agh.awi.persistence.model.weather_condition.Hazard converted = HazardConverter.convert(hazard);

        assertNotNull(converted);
        assertEquals(converted.getSeverity(), SEVERITY);
        assertEquals(converted.getType(), TYPE);
    }

}