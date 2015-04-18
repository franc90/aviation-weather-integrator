package pl.edu.agh.awi.scheduled.tasks;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.edu.agh.awi.downloader.weather.metar.generated.Response;
import pl.edu.agh.awi.persistence.model.AirPort;
import pl.edu.agh.awi.scheduled.CronHelper;

import java.util.List;

@Component
public class MetarTask extends AirportTask<Response> {

    private static final int PORTION_SIZE = 50;

    @Override
    public int getPortionSize() {
        return PORTION_SIZE;
    }

    @Override
    protected void saveResponse(List<AirPort> airPorts, Response response) {

    }

    @Scheduled(cron = CronHelper.METAR_CRON)
    public void task() {
        super.task();
    }

}
