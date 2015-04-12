package pl.edu.agh.awi.scheduled.tasks;

import com.hazelcast.core.IMap;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.edu.agh.awi.scheduled.CronHelper;
import pl.edu.agh.awi.scheduled.cache.CachedFlight;

@Component
public class FlightDetailsTask extends AbstractHazelcastTask {

    private IMap<String, CachedFlight> flights;

    private IMap<String, CachedFlight> finishedFlights;

    @Override
    public void init() {
        flights = hazelcast.getMap("flights");
        finishedFlights = hazelcast.getMap("finishedFlights");
    }

    @Scheduled(cron = CronHelper.EVERY_QUARTER)
    public void task() {

    }

}
