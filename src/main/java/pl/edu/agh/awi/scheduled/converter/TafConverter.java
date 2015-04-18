package pl.edu.agh.awi.scheduled.converter;

import org.springframework.util.CollectionUtils;
import pl.edu.agh.awi.downloader.weather.taf.generated.Forecast;
import pl.edu.agh.awi.downloader.weather.taf.generated.Response;
import pl.edu.agh.awi.downloader.weather.taf.generated.SkyCondition;
import pl.edu.agh.awi.downloader.weather.taf.generated.TAF;
import pl.edu.agh.awi.persistence.model.weather_condition.Taf;
import pl.edu.agh.awi.scheduled.helper.DateHelper;

import java.util.*;

public class TafConverter {
    public static Map<String, List<Taf>> convert(Response response) {
        if (response == null || response.getData() == null || CollectionUtils.isEmpty(response.getData().getTAF())) {
            return Collections.emptyMap();
        }

        List<TAF> tafs = response.getData().getTAF();
        Map<String, List<Taf>> results = new HashMap<>(tafs.size());
        tafs.forEach(taf -> {
            if (!CollectionUtils.isEmpty(taf.getForecast())) {
                List<Taf> converted = new ArrayList<>(taf.getForecast().size());

                taf.getForecast().forEach(forecast -> {
                    Taf result = convert(forecast);
                    converted.add(result);
                });

                results.put(taf.getStationId(), converted);
            }
        });

        return results;
    }

    private static Taf convert(Forecast source) {
        Taf taf = new Taf();

        taf.setValidFrom(DateHelper.getDate(source.getFcstTimeFrom()));
        taf.setValidTo(DateHelper.getDate(source.getFcstTimeTo()));

        if (!CollectionUtils.isEmpty(source.getSkyCondition())) {
            Integer cloudBaseFtAgl = source.getSkyCondition().get(0).getCloudBaseFtAgl();
            if (cloudBaseFtAgl != null) {
                taf.setCloudHeight(cloudBaseFtAgl.doubleValue());
            }
        }
        if (source.getAltimInHg() != null) {
            taf.setPressure(source.getAltimInHg().doubleValue());
        }
        if (!CollectionUtils.isEmpty(source.getTemperature())) {
            Float surfaceTemp = source.getTemperature().get(0).getSfcTempC();
            if (surfaceTemp != null) {
                taf.setTemperature(surfaceTemp.doubleValue());
            }
        }
        if (source.getVertVisFt() != null) {
            taf.setVerticalVisibility(source.getVertVisFt().intValue());
        }
        if (source.getVisibilityStatuteMi() != null) {
            taf.setVisibilityStatute(source.getVisibilityStatuteMi().doubleValue());
        }
        if (source.getWindDirDegrees() != null) {
            taf.setWindDirection(source.getWindDirDegrees().toString());
        }
        if (source.getWindGustKt() != null) {
            taf.setWindGust(source.getWindGustKt().doubleValue());
        }
        if (source.getWindSpeedKt() != null) {
            taf.setWindSpeed(source.getWindSpeedKt().doubleValue());
        }

        return taf;
    }


}
