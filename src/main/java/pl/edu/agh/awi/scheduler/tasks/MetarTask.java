package pl.edu.agh.awi.scheduler.tasks;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import pl.edu.agh.awi.downloader.weather.metar.generated.Response;
import pl.edu.agh.awi.persistence.model.AirPort;
import pl.edu.agh.awi.persistence.model.weather_condition.Metar;
import pl.edu.agh.awi.scheduler.converter.MetarConverter;
import pl.edu.agh.awi.scheduler.helper.CronHelper;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

@Component
public class MetarTask extends AirportTask<Response> {

    private final Logger logger = Logger.getLogger("MetarTask");

    private static final int PORTION_SIZE = 50;

    @Override
    public int getPortionSize() {
        return PORTION_SIZE;
    }

    @Scheduled(cron = CronHelper.METAR_CRON)
    public void task() {
        super.task();
    }

    @Override
    protected void saveResponse(List<AirPort> airPorts, Response response) {
        Map<String, List<Metar>> metars = MetarConverter.convert(response);

        Set<AirPort> updatedAirPorts = new HashSet<>();
        metars.forEach((airportIcao, metarList) -> {
            AirPort airPort = addMetars(airPorts, airportIcao, metarList);
            if (airPort != null) {
                updatedAirPorts.add(airPort);
            }
        });

        if (!CollectionUtils.isEmpty(updatedAirPorts)) {
            updatedAirPorts.forEach(persistenceService::saveAirport);
        }
    }

    private AirPort addMetars(List<AirPort> airPorts, String airportIcao, List<Metar> metarList) {
        AirPort airPort = getAirPort(airPorts, airportIcao);
        if (airPort == null) {
            return null;
        }

        int airportMetars = airPort.getMetars().size();
        //FIXME: is there not a better way to check if metar not already in airport?
        metarList.stream().filter(metar -> notContains(airPort, metar)).forEach(airPort::addMetar);

        boolean noNewMetars = airportMetars == airPort.getMetars().size();

        logger.info("Saving new metars? " + noNewMetars);
        return noNewMetars ? null : airPort;
    }

    private boolean notContains(AirPort airPort, Metar metar) {
        for (Metar m : airPort.getMetars()) {
            if (DateUtils.isSameInstant(m.getTimestamp(), metar.getTimestamp())) {
                return false;
            }
        }
        return true;
    }

}
