package pl.edu.agh.awi.scheduler.converter;

import pl.edu.agh.awi.persistence.model.weather_condition.Hazard;

public class HazardConverter {

    public static Hazard convert(pl.edu.agh.awi.downloader.weather.airsigmet.generated.Hazard source) {
        Hazard hazard = new Hazard();

        hazard.setSeverity(source.getSeverity());
        hazard.setType(source.getType());

        return hazard;
    }

}
