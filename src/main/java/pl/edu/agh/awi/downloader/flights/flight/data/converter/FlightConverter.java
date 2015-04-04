package pl.edu.agh.awi.downloader.flights.flight.data.converter;

import pl.edu.agh.awi.downloader.exceptions.ResponseConverterException;
import pl.edu.agh.awi.downloader.flights.flight.data.Flight;

import java.util.Date;
import java.util.List;

public class FlightConverter {

    public static Flight convert(String key, List<Object> value) {
        if (value == null || value.size() != 18) {
            throw new ResponseConverterException("Could not parse flight: " + key);
        }

        Flight flight = new Flight.FlightBuilder()
                .setFlightId(key)
                .setRegistration(getStringValue(value.get(0)))
                .setLatitude(getDoubleValue(value.get(1)))
                .setLongitude(getDoubleValue(value.get(2)))
                .setTrack(getIntegerValue(value.get(3)))
                .setAltitude(getIntegerValue(value.get(4)))
                .setSpeed(getIntegerValue(value.get(5)))
                .setSquawk(getStringValue(value.get(6)))
                .setRadar(getStringValue(value.get(7)))
                .setAircraft(getStringValue(value.get(8)))
                .setRegistration2(getStringValue(value.get(9)))
                .setTimestamp(getDate(value.get(10)))
                .setDepartureIata(getStringValue(value.get(11)))
                .setArrivalIata(getStringValue(value.get(12)))
                .setFlightDesignator(getStringValue(value.get(13)))
                .setVerticalSpeed(getIntegerValue(value.get(15)))
                .setFlightTracker(getStringValue(value.get(16)))
                .createFlight();

        return flight;
    }

    public static Date getDate(Object obj) {
        if (obj instanceof Integer) {
            Integer i = (Integer) obj;
            long timestamp = i.longValue() * 1000L;
            return new Date(timestamp);
        }
        return new Date(0);
    }

    public static String getStringValue(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        return "";
    }

    public static Double getDoubleValue(Object obj) {
        if (obj instanceof Double) {
            return (Double) obj;
        }
        if (obj instanceof Float) {
            return (Double) obj;
        }
        return .0;
    }

    public static Integer getIntegerValue(Object obj) {
        if (obj instanceof Integer) {
            return (Integer) obj;
        }
        return 0;
    }
}
