package pl.edu.agh.awi.scheduler.tasks;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import pl.edu.agh.awi.downloader.weather.taf.generated.Response;
import pl.edu.agh.awi.persistence.model.AirPort;
import pl.edu.agh.awi.persistence.model.weather_condition.Taf;
import pl.edu.agh.awi.scheduler.converter.TafConverter;
import pl.edu.agh.awi.scheduler.helper.CronHelper;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

@Component
public class TafTask extends AirportTask<Response> {

    private final Logger logger = Logger.getLogger("TafTask");

    private static final int PORTION_SIZE = 25;

    @Override
    public int getPortionSize() {
        return PORTION_SIZE;
    }

    @Scheduled(cron = CronHelper.TAF_CRON)
    public void task() {
        super.task();
    }

    @Override
    protected void saveResponse(List<AirPort> airPorts, Response response) {
        Map<String, List<Taf>> tafs = TafConverter.convert(response);

        Set<AirPort> updatedAirPorts = new HashSet<>();
        tafs.forEach((airportIcao, tafList) -> {
            AirPort airPort = addTafs(airPorts, airportIcao, tafList);
            if (airPort != null) {
                updatedAirPorts.add(airPort);
            }
        });

        if (!CollectionUtils.isEmpty(updatedAirPorts)) {
            updatedAirPorts.forEach(persistenceService::saveAirport);
        }
    }

    private AirPort addTafs(List<AirPort> airPorts, String airportIcao, List<Taf> tafs) {
        AirPort airPort = getAirPort(airPorts, airportIcao);
        if (airPort == null) {
            return null;
        }

        int airportTafs = airPort.getTafs().size();
        //BETTER
        tafs.stream()
                .filter(
                        taf -> persistenceService.isTafNotConnectedWithAirPort(taf, airPort))
                .forEach(airPort::addTaf);

        boolean noNewTafs = airportTafs == airPort.getTafs().size();

        logger.info("Saving new tafs? " + noNewTafs);
        return noNewTafs ? null : airPort;
    }

}
