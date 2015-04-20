package pl.edu.agh.awi.scheduler.converter;

import org.springframework.util.CollectionUtils;
import pl.edu.agh.awi.downloader.weather.taf.generated.*;
import pl.edu.agh.awi.persistence.model.weather_condition.Taf;
import pl.edu.agh.awi.scheduler.helper.DateHelper;

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

        Optional<Forecast> optional = Optional.ofNullable(source);

        optional.flatMap(o -> Optional.ofNullable(o.getIcingCondition()))
                .ifPresent(val -> val
                        .stream()
                        .map(TafConverter::convert)
                        .forEach(taf::addIcingCondition));

        optional.flatMap(o -> Optional.ofNullable(o.getSkyCondition()))
                .ifPresent(val -> val
                        .stream()
                        .map(TafConverter::convert)
                        .forEach(taf::addSkyCondition));

        optional.flatMap(o -> Optional.ofNullable(o.getTurbulenceCondition()))
                .ifPresent(val -> val
                        .stream()
                        .map(TafConverter::convert)
                        .forEach(taf::addTurbulenceCondition));

        optional.flatMap(o -> Optional.ofNullable(o.getAltimInHg()))
                .map(Float::doubleValue)
                .ifPresent(taf::setPressure);

        optional.flatMap(o -> Optional.ofNullable(o.getTemperature()))
                .ifPresent(val -> val.stream()
                        .mapToDouble(Temperature::getSfcTempC)
                        .min()
                        .ifPresent(taf::setTemperature));

        optional.flatMap(o -> Optional.ofNullable(o.getVertVisFt()))
                .map(Short::intValue)
                .ifPresent(taf::setVerticalVisibility);

        optional.flatMap(o -> Optional.ofNullable(o.getVisibilityStatuteMi()))
                .map(Float::doubleValue)
                .ifPresent(taf::setVisibilityStatute);

        optional.flatMap(o -> Optional.ofNullable(o.getWindDirDegrees()))
                .map(String::valueOf)
                .ifPresent(taf::setWindDirection);

        optional.flatMap(o -> Optional.ofNullable(o.getWindGustKt()))
                .map(Integer::doubleValue)
                .ifPresent(taf::setWindGust);

        optional.flatMap(o -> Optional.ofNullable(o.getWindSpeedKt()))
                .map(Integer::doubleValue)
                .ifPresent(taf::setWindSpeed);

        return taf;
    }

    private static pl.edu.agh.awi.persistence.model.weather_condition.TurbulenceCondition convert(TurbulenceCondition source) {
        pl.edu.agh.awi.persistence.model.weather_condition.TurbulenceCondition turbulenceCondition = new pl.edu.agh.awi.persistence.model.weather_condition.TurbulenceCondition();

        turbulenceCondition.setTurbulenceIntensity(source.getTurbulenceIntensity());
        turbulenceCondition.setTurbulenceMaxAltitude(source.getTurbulenceMaxAltFtAgl());
        turbulenceCondition.setTurbulenceMinAltitude(source.getTurbulenceMinAltFtAgl());

        return turbulenceCondition;
    }

    private static pl.edu.agh.awi.persistence.model.weather_condition.IcingCondition convert(IcingCondition source) {
        pl.edu.agh.awi.persistence.model.weather_condition.IcingCondition icingCondition = new pl.edu.agh.awi.persistence.model.weather_condition.IcingCondition();

        icingCondition.setIcingIntensity(source.getIcingIntensity());
        icingCondition.setIcingMaxAltitude(source.getIcingMaxAltFtAgl());
        icingCondition.setIcingMinAltitude(source.getIcingMinAltFtAgl());

        return icingCondition;
    }

    public static pl.edu.agh.awi.persistence.model.weather_condition.SkyCondition convert(SkyCondition source) {
        pl.edu.agh.awi.persistence.model.weather_condition.SkyCondition skyCondition = new pl.edu.agh.awi.persistence.model.weather_condition.SkyCondition();

        skyCondition.setCloudBase(source.getCloudBaseFtAgl());
        skyCondition.setSkyCover(source.getSkyCover());

        return skyCondition;
    }

}
