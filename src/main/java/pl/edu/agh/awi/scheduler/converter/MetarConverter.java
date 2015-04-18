package pl.edu.agh.awi.scheduler.converter;

import org.springframework.util.CollectionUtils;
import pl.edu.agh.awi.downloader.weather.metar.generated.METAR;
import pl.edu.agh.awi.downloader.weather.metar.generated.Response;
import pl.edu.agh.awi.downloader.weather.metar.generated.SkyCondition;
import pl.edu.agh.awi.persistence.model.weather_condition.Metar;
import pl.edu.agh.awi.scheduler.helper.DateHelper;

import java.util.*;

public class MetarConverter {
    public static Map<String, List<Metar>> convert(Response response) {
        if (response == null || response.getData() == null || CollectionUtils.isEmpty(response.getData().getMETAR())) {
            return Collections.emptyMap();
        }

        List<METAR> metarList = response.getData().getMETAR();
        Map<String, List<Metar>> converted = new HashMap<>(metarList.size());
        metarList.forEach(metar -> {
            List<Metar> metars = getList(converted, metar.getStationId());
            metars.add(convert(metar));
        });

        return converted;
    }

    private static List<Metar> getList(Map<String, List<Metar>> converted, String airportIcao) {
        List<Metar> metars = converted.get(airportIcao);

        if (CollectionUtils.isEmpty(metars)) {
            metars = new ArrayList<>();
            converted.put(airportIcao, metars);
        }

        return metars;
    }

    private static Metar convert(METAR source) {
        Metar metar = new Metar();

        metar.setTimestamp(DateHelper.getDate(source.getObservationTime()));
        metar.setVerticalVisibility(source.getVertVisFt());
        metar.setInfoType(source.getMetarType());

        if (!CollectionUtils.isEmpty(source.getSkyCondition())) {
            source.getSkyCondition().stream().map(MetarConverter::convert).forEach(metar::addSkyCondition);
        }

        if (source.getDewpointC() != null) {
            metar.setDewPointTemperature(source.getDewpointC().doubleValue());
        }

        if (source.getSeaLevelPressureMb() != null) {
            metar.setPressure(source.getSeaLevelPressureMb().doubleValue());
        }

        if (source.getTempC() != null) {
            metar.setTemperature(source.getTempC().doubleValue());
        }

        if (source.getVisibilityStatuteMi() != null) {
            metar.setVisibilityStatute(source.getVisibilityStatuteMi().doubleValue());
        }

        if (source.getWindDirDegrees() != null) {
            metar.setWindDirection(source.getWindDirDegrees().toString());
        }

        if (source.getWindGustKt() != null) {
            metar.setWindGust(source.getWindGustKt().doubleValue());
        }

        if (source.getWindSpeedKt() != null) {
            metar.setWindSpeed(source.getWindSpeedKt().doubleValue());
        }

        return metar;
    }

    private static pl.edu.agh.awi.persistence.model.weather_condition.SkyCondition convert(SkyCondition source) {
        pl.edu.agh.awi.persistence.model.weather_condition.SkyCondition skyCondition = new pl.edu.agh.awi.persistence.model.weather_condition.SkyCondition();

        skyCondition.setCloudBase(source.getCloudBaseFtAgl());
        skyCondition.setSkyCover(source.getSkyCover());

        return skyCondition;
    }
}
