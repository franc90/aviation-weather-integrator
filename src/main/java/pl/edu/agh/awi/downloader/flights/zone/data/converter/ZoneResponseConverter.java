package pl.edu.agh.awi.downloader.flights.zone.data.converter;

import pl.edu.agh.awi.downloader.exceptions.ResponseConverterException;
import pl.edu.agh.awi.downloader.flights.zone.data.Zone;
import pl.edu.agh.awi.downloader.flights.zone.data.ZoneResponse;

import java.util.*;

public class ZoneResponseConverter {

    public static final String VERSION = "version";

    public static ZoneResponse convert(Map<String, Object> parsed) {
        Set<Map.Entry<String, Object>> entries = parsed.entrySet();

        Long version = null;
        List<Zone> zones = new LinkedList<>();
        for (Map.Entry<String, Object> entry : entries) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (VERSION.equals(key)) {
                version = getValue(value);
            } else {
                Zone zone = ZoneConverter.convert(key, (HashMap<String, Object>) value);
                zones.add(zone);
            }
        }

        if (version != null) {
            return new ZoneResponse(version, zones);
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
