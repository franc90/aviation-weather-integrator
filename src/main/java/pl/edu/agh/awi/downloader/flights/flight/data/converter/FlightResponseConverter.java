package pl.edu.agh.awi.downloader.flights.flight.data.converter;

import pl.edu.agh.awi.downloader.exceptions.ResponseConverterException;
import pl.edu.agh.awi.downloader.flights.flight.data.Flight;
import pl.edu.agh.awi.downloader.flights.flight.data.FlightResponse;

import java.util.*;

public class FlightResponseConverter {

    private static final String VERSION = "version";

    private static final String FLIGHTS_COUNT = "full_count";

    public static FlightResponse convert(Map<String, Object> parsed) {
        Set<Map.Entry<String, Object>> entries = parsed.entrySet();

        Long version = null;
        Long flightsCount = null;
        List<Flight> flights = new LinkedList<>();
        for (Map.Entry<String, Object> entry : entries) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (VERSION.equals(key)) {
                version = getValue(value);
            } else if (FLIGHTS_COUNT.equals(key)) {
                flightsCount = getValue(value);
            } else {
                Flight flight = FlightConverter.convert(key, (ArrayList<Object>) value);
                flights.add(flight);
            }
        }

        if (version != null) {
            return new FlightResponse(version, flightsCount, flights);
        }

        throw new ResponseConverterException("Could not convert zones.");
    }

    private static Long getValue(Object value) {
        if (value instanceof Integer) {
            Integer i = (Integer) value;
            return i.longValue();
        }
        if (value instanceof Long) {
            return (Long) value;
        }

        throw new ResponseConverterException("Could not parse version " + value);
    }


}
