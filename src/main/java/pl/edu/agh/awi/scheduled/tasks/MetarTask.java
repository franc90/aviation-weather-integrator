package pl.edu.agh.awi.scheduled.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.edu.agh.awi.downloader.weather.AbstractWeatherClient;
import pl.edu.agh.awi.downloader.weather.metar.generated.Response;
import pl.edu.agh.awi.downloader.weather.parameters.Stations;
import pl.edu.agh.awi.scheduled.CronHelper;

@Component
public class MetarTask {

    @Autowired
    private AbstractWeatherClient<Response, Stations> client;

    @Scheduled(cron = CronHelper.METAR_CRON)
    public void task() {

    }
}
