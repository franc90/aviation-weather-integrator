package pl.edu.agh.awi.scheduler.tasks;

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
import pl.edu.agh.awi.scheduler.helper.CronHelper;
import pl.edu.agh.awi.scheduler.helper.ZoneHelper;
import pl.edu.agh.awi.scheduler.helper.airsigmet.AreaHelper;
import pl.edu.agh.awi.scheduler.helper.airsigmet.PathHelper;

import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AirSigMetTask {

    public static final String NORTH_AMERICA = "northamerica";

    @Autowired
    private AbstractWeatherClient<Response, RectangularRegion> client;

    @Autowired
    private PersistenceService persistenceService;

    @Autowired
    private ZoneHelper zoneHelper;

    @Scheduled(cron = CronHelper.AIRSIGMET_CRON)
    public void task() {
        Response response = getResponse();
        List<AirPort> airPorts = loadAirports();
        convertAndSave(response, airPorts);
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
        if (response == null || response.getData() == null || CollectionUtils.isEmpty(response.getData().getAIRSIGMET())) {
            return;
        }

        List<AIRSIGMET> airsigmets = response.getData().getAIRSIGMET();
        airsigmets.forEach(e -> convertAndSave(e, airPorts));
    }

    private void convertAndSave(AIRSIGMET airsigmet, List<AirPort> airPorts) {
        List<AirPort> airsigmetAirports = getRelevantAirports(airsigmet, airPorts);
        AirSigmet airSigmet = AirSigmetConverter.convert(airsigmet);

        airsigmetAirports.forEach(e -> e.addAirSigmet(airSigmet));
        airsigmetAirports.forEach(persistenceService::saveAirport);
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
