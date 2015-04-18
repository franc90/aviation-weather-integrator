package pl.edu.agh.awi.scheduler.tasks;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.edu.agh.awi.downloader.weather.metar.generated.Response;
import pl.edu.agh.awi.persistence.model.AirPort;
import pl.edu.agh.awi.persistence.model.weather_condition.Metar;
import pl.edu.agh.awi.scheduler.converter.MetarConverter;
import pl.edu.agh.awi.scheduler.helper.CronHelper;

import java.util.List;
import java.util.Map;
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
        metars.forEach((airportIcao, metarList) -> addMetars(airPorts, airportIcao, metarList));
    }

    private void addMetars(List<AirPort> airPorts, String airportIcao, List<Metar> metarList) {
        AirPort airPort = getAirPort(airPorts, airportIcao);
        if (airPort == null) {
            return;
        }

        //BETTER ?
        metarList.stream()
                .filter(
                        metar -> persistenceService.isMetarNotConnectedWithAirPort(metar, airPort))
                .forEach(m -> {
                    logger.info("METAR " + airPort.getIataCode() + " - " + m.getTimestamp());
                    persistenceService.addMetar(airPort, m);
                });
    }

}
