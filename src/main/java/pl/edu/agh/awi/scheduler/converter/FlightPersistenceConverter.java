package pl.edu.agh.awi.scheduler.converter;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.awi.persistence.PersistenceService;
import pl.edu.agh.awi.persistence.model.AirLine;
import pl.edu.agh.awi.persistence.model.AirPort;
import pl.edu.agh.awi.persistence.model.Flight;
import pl.edu.agh.awi.scheduler.exception.SchedulerException;
import pl.edu.agh.awi.scheduler.helper.flight.SingleValueDownloaderHelper;

@Component
public class FlightPersistenceConverter {

    @Autowired
    private PersistenceService persistenceService;

    private SingleValueDownloaderHelper<AirLine, String> airlineIataDownloader = new SingleValueDownloaderHelper<AirLine, String>() {
        @Override
        protected AirLine getFromRepository(String key) {
            return persistenceService.findAirLineByIataCode(key);
        }
    };

    private SingleValueDownloaderHelper<AirLine, String> airlineIcaoDownloader = new SingleValueDownloaderHelper<AirLine, String>() {
        @Override
        protected AirLine getFromRepository(String key) {
            return persistenceService.findAirLineByIcaoCode(key);
        }
    };

    private SingleValueDownloaderHelper<AirPort, String> airportIataDownloader = new SingleValueDownloaderHelper<AirPort, String>() {
        @Override
        protected AirPort getFromRepository(String key) {
            return persistenceService.findAirPortByIataCode(key);
        }
    };

    public Flight convert(pl.edu.agh.awi.downloader.flights.flight.data.Flight source) {

        Flight flight = new Flight();

        AirLine airLine = getAirline(source.getFlightDesignator(), source.getFlightTracker());
        AirPort departureAirport = airportIataDownloader.download(source.getDepartureIata());
        AirPort arrivalAirport = airportIataDownloader.download(source.getArrivalIata());

        flight.setFlightId(source.getFlightId());
        flight.setAircraft(source.getAircraft());
        flight.setAirLine(airLine);
        flight.setDepartureAirport(departureAirport);
        flight.addDestinationAirPort(arrivalAirport);

        return flight;
    }

    private AirLine getAirline(String flightDesignator, String flightTracker) {
        if (StringUtils.isNotBlank(flightDesignator) && flightDesignator.length() >= 2) {
            String iata = flightDesignator.substring(0, 2);
            return airlineIataDownloader.download(iata);
        }
        if (StringUtils.isNotBlank(flightTracker) && flightTracker.length() >= 3) {
            String icao = flightTracker.substring(0, 3);
            return airlineIcaoDownloader.download(icao);
        }

        throw new SchedulerException("Cannot parse - neither IACO nor IATA present");
    }
}
