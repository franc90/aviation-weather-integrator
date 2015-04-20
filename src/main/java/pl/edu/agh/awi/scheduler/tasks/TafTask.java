package pl.edu.agh.awi.scheduler.tasks;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.edu.agh.awi.downloader.weather.taf.generated.Response;
import pl.edu.agh.awi.persistence.model.AirPort;
import pl.edu.agh.awi.persistence.model.weather_condition.Taf;
import pl.edu.agh.awi.scheduler.converter.TafConverter;
import pl.edu.agh.awi.scheduler.helper.CronHelper;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        tafs.forEach((airportIcao, tafList) -> addTafs(airPorts, airportIcao, tafList));
    }

    private void addTafs(List<AirPort> airPorts, String airportIcao, List<Taf> tafs) {
        Optional<AirPort> airPortOptional = Optional.ofNullable(getAirPort(airPorts, airportIcao));

        airPortOptional.ifPresent(airPort ->
                tafs.stream()
                        .filter(
                                taf -> persistenceService.isTafNotConnectedWithAirPort(taf, airPort))
                        .forEach(t -> {
                            logger.info("TAF " + airPort.getIataCode() + " - " + t.getValidFrom());
                            persistenceService.addTaf(airPort, t);
                        }));

    }

}
