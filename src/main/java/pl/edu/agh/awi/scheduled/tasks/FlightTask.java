package pl.edu.agh.awi.scheduled.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.edu.agh.awi.downloader.flights.AbstractFlightsClient;
import pl.edu.agh.awi.downloader.flights.flight.data.FlightResponse;
import pl.edu.agh.awi.downloader.flights.flightDetails.data.FlightDetailsResponse;
import pl.edu.agh.awi.scheduled.CronHelper;

@Component
public class FlightTask {

    @Autowired
    AbstractFlightsClient<FlightResponse, ?> flightsClient;

    @Autowired
    AbstractFlightsClient<FlightDetailsResponse, ?> flightDetailsClient;

    @Scheduled(cron = CronHelper.EVERY_QUARTER)
    public void task() {

    }

}
