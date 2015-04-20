package pl.edu.agh.awi.scheduler.tasks;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import pl.edu.agh.awi.downloader.weather.AbstractWeatherClient;
import pl.edu.agh.awi.downloader.weather.airsigmet.generated.AIRSIGMET;
import pl.edu.agh.awi.downloader.weather.airsigmet.generated.Response;
import pl.edu.agh.awi.downloader.weather.parameters.RectangularRegion;
import pl.edu.agh.awi.downloader.weather.parameters.RectangularRegionBuilder;
import pl.edu.agh.awi.downloader.weather.parameters.RequestParameters;
import pl.edu.agh.awi.persistence.PersistenceService;
import pl.edu.agh.awi.persistence.model.AirPort;
import pl.edu.agh.awi.persistence.model.Zone;
import pl.edu.agh.awi.persistence.model.weather_condition.AirSigmet;
import pl.edu.agh.awi.scheduler.converter.AirSigmetConverter;
import pl.edu.agh.awi.scheduler.exception.SchedulerException;
import pl.edu.agh.awi.scheduler.helper.CronHelper;
import pl.edu.agh.awi.scheduler.helper.ZoneHelper;
import pl.edu.agh.awi.scheduler.helper.airsigmet.AreaHelper;
import pl.edu.agh.awi.scheduler.helper.airsigmet.PathHelper;

import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class AirSigMetTask {

    private final Logger logger = Logger.getLogger("AirSigMetTask");

    public static final String NORTH_AMERICA = "northamerica";

    @Autowired
    private AbstractWeatherClient<Response, RectangularRegion> client;

    @Autowired
    private PersistenceService persistenceService;

    @Autowired
    private ZoneHelper zoneHelper;

    @Scheduled(cron = CronHelper.AIRSIGMET_CRON)
    public void task() {
        try {
            Response response = getResponse();
            List<AirPort> airPorts = loadAirports();
            if (!CollectionUtils.isEmpty(airPorts)) {
                convertAndSave(response, airPorts);
            }
        } catch (SchedulerException ex) {
            logger.info(ex.getMessage());
        }
    }

    private Response getResponse() {
        Zone zone = zoneHelper.loadZone(NORTH_AMERICA);
        RequestParameters<RectangularRegion> parameters = new RequestParameters.RequestParametersBuilder<RectangularRegion>()
                .setHoursBeforeNow(1)
                .setValue(new RectangularRegionBuilder()
                        .setMinimalLatitude(zone.getMinimalLatitude())
                        .setMaximalLatitude(zone.getMaximalLatitude())
                        .setMinimalLongitude(zone.getMinimalLongitude())
                        .setMaximalLongitude(zone.getMaximalLongitude())
                        .build())
                .build();

        return client.getResponse(parameters);
    }

    protected List<AirPort> loadAirports() {
        Collection<AirPort> airPorts = persistenceService.findAllAirPorts();
        return new ArrayList<>(airPorts);
    }

    private void convertAndSave(Response response, List<AirPort> airPorts) {
        Optional
                .ofNullable(response)
                .flatMap(o -> Optional.ofNullable(o.getData()))
                .flatMap(o -> Optional.ofNullable(o.getAIRSIGMET()))
                .ifPresent(airsigmets -> airsigmets.forEach(airsigmet -> convertAndSave(airsigmet, airPorts)));
    }

    private void convertAndSave(AIRSIGMET airsigmet, List<AirPort> airPorts) {
        List<AirPort> airsigmetAirports = getRelevantAirports(airsigmet, airPorts);
        AirSigmet airSigmet = AirSigmetConverter.convert(airsigmet);

        logger.info("Saving airsigmet " + getAirsigmetName(airSigmet) + " for " + airsigmetAirports.size() + " airports");
        airsigmetAirports.forEach(airport -> persistenceService.addAirsigmet(airport, airSigmet));
    }

    private String getAirsigmetName(AirSigmet airSigmet) {
        if (airSigmet.getHazard() != null && StringUtils.isNotBlank(airSigmet.getHazard().getType())) {
            return airSigmet.getHazard().getType();
        }

        return airSigmet.getType().toString();
    }

    private List<AirPort> getRelevantAirports(AIRSIGMET airsigmet, List<AirPort> airPorts) {
        List<Path2D> areas = PathHelper.ofAreas(airsigmet.getArea());
        List<AirPort> filteredAirports = airPorts
                .stream()
                .filter(e -> AreaHelper.contains(areas, e.getLatitude(), e.getLongitude()))
                .collect(Collectors.toList());

        return filteredAirports;
    }


}
