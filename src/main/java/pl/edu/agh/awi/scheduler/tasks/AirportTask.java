package pl.edu.agh.awi.scheduler.tasks;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import pl.edu.agh.awi.downloader.weather.AbstractWeatherClient;
import pl.edu.agh.awi.downloader.weather.parameters.RequestParameters;
import pl.edu.agh.awi.downloader.weather.parameters.Stations;
import pl.edu.agh.awi.downloader.weather.parameters.StationsBuilder;
import pl.edu.agh.awi.persistence.PersistenceService;
import pl.edu.agh.awi.persistence.model.AirPort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AirportTask<T> {

    @Autowired
    private AbstractWeatherClient<T, Stations> client;

    @Autowired
    protected PersistenceService persistenceService;

    public abstract int getPortionSize();

    protected abstract void saveResponse(List<AirPort> airPorts, T response);

    public void task() {
        List<List<AirPort>> partitionedAirports = loadPartitionedAirports(getPortionSize());
        partitionedAirports.stream().forEach(this::downloadAndSaveResponse);
    }

    protected List<List<AirPort>> loadPartitionedAirports(int partitionSize) {
        List<AirPort> airPorts = loadAirports();

        if (CollectionUtils.isEmpty(airPorts)) {
            return Collections.emptyList();
        }

        return Lists.partition(
                airPorts.stream()
                        .filter(e -> !Strings.isNullOrEmpty(e.getIcaoCode()))
                        .collect(Collectors.toList()),
                partitionSize);
    }

    private List<AirPort> loadAirports() {
        Collection<AirPort> airPorts = persistenceService.findAllAirPorts();
        return new ArrayList<>(airPorts);
    }

    protected void downloadAndSaveResponse(List<AirPort> airPorts) {
        StationsBuilder stations = new StationsBuilder();
        airPorts.forEach(e -> stations.addStation(e.getIcaoCode()));

        RequestParameters<Stations> params = new RequestParameters.RequestParametersBuilder<Stations>()
                .setHoursBeforeNow(2)
                .setValue(stations.build())
                .build();

        T response = client.getResponse(params);
        saveResponse(airPorts, response);
    }

    protected AirPort getAirPort(List<AirPort> airPorts, String airportIcao) {
        for (AirPort airPort : airPorts) {
            if (airportIcao.equals(airPort.getIcaoCode())) {
                return airPort;
            }
        }

        return null;
    }

}
