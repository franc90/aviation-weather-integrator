package pl.edu.agh.awi.scheduled.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.edu.agh.awi.downloader.weather.AbstractWeatherClient;
import pl.edu.agh.awi.downloader.weather.airsigmet.generated.Response;
import pl.edu.agh.awi.downloader.weather.parameters.RectangularRegion;
import pl.edu.agh.awi.scheduled.CronHelper;

@Component
public class AirSigMetTask {

    @Autowired
    private AbstractWeatherClient<Response, RectangularRegion> client;

    @Scheduled(cron = CronHelper.AIRSIGMET_CRON)
    public void task() {

    }

}
