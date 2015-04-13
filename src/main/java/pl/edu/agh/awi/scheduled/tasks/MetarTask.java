package pl.edu.agh.awi.scheduled.tasks;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import pl.edu.agh.awi.downloader.weather.AbstractWeatherClient;
import pl.edu.agh.awi.downloader.weather.metar.generated.Response;
import pl.edu.agh.awi.downloader.weather.parameters.RequestParameters;
import pl.edu.agh.awi.downloader.weather.parameters.Stations;
import pl.edu.agh.awi.downloader.weather.parameters.StationsBuilder;
import pl.edu.agh.awi.persistence.model.AirPort;
import pl.edu.agh.awi.scheduled.CronHelper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MetarTask {

    public static final int PORTION_SIZE = 50;

    @Autowired
    private AbstractWeatherClient<Response, Stations> client;

    @Scheduled(cron = CronHelper.METAR_CRON)
    public void task() {
        List<AirPort> airPorts = loadAirports();
        List<List<AirPort>> partitionedAirports = partition(airPorts, PORTION_SIZE);

        partitionedAirports.stream().forEach(this::downloadAndSaveMetars);
    }

    private List<AirPort> loadAirports() {
        //TODO: implement when service ready
        return Collections.emptyList();
    }

    private List<List<AirPort>> partition(List<AirPort> airPorts, int portionSize) {
        if (CollectionUtils.isEmpty(airPorts)) {
            return Collections.emptyList();
        }

        return Lists.partition(
                airPorts.stream()
                        .filter(e -> !Strings.isNullOrEmpty(e.getIcaoCode()))
                        .collect(Collectors.toList()),
                PORTION_SIZE);
    }

    private void downloadAndSaveMetars(List<AirPort> airPorts) {
        StationsBuilder stations = new StationsBuilder();
        airPorts.forEach(e -> stations.addStation(e.getIcaoCode()));

        RequestParameters<Stations> params = new RequestParameters.RequestParametersBuilder<Stations>()
                .setHoursBeforeNow(2)
                .setValue(stations.build())
                .build();

        Response response = client.getResponse(params);
        saveMetars(response);
    }

    private void saveMetars(Response response) {
        //TODO: convert + save when repo ready.
    }


}
