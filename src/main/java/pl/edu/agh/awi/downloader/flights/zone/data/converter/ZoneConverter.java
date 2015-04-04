package pl.edu.agh.awi.downloader.flights.zone.data.converter;

import com.google.common.collect.ImmutableList;
import pl.edu.agh.awi.downloader.exceptions.ZoneResponseConverterException;
import pl.edu.agh.awi.downloader.flights.zone.data.Zone;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ZoneConverter {

    public static final String MINIMAL_LONGITUDE = "tl_x";

    public static final String MINIMAL_LATITUDE = "br_y";

    public static final String MAXIMAL_LONGITUDE = "br_x";

    public static final String MAXIMAL_LATITUDE = "tl_y";

    public static final String SUBZONES = "subzones";

    public static Zone convert(String zoneName, Map<String, Object> value) {
        Double minimalLongitude = getValue(value.get(MINIMAL_LONGITUDE));
        Double minimalLatitude = getValue(value.get(MINIMAL_LATITUDE));
        Double maximalLongitude = getValue(value.get(MAXIMAL_LONGITUDE));
        Double maximalLatitude = getValue(value.get(MAXIMAL_LATITUDE));
        Map<String, Object> subZones = (Map<String, Object>) value.get(SUBZONES);

        Zone zone = new Zone(zoneName, minimalLatitude, maximalLatitude, minimalLongitude, maximalLongitude, convert(subZones));
        return zone;
    }

    private static Double getValue(Object o) {
        if (o instanceof Double) {
            return (Double) o;
        }
        if (o instanceof Integer) {
            Integer i = (Integer) o;
            return Double.valueOf(i);
        }

        throw new ZoneResponseConverterException("Could not convert " + o);
    }

    private static List<Zone> convert(Map<String, Object> subZones) {
        if (subZones == null || subZones.isEmpty()) {
            return ImmutableList.<Zone>of();
        }

        List<Zone> zones = new ArrayList<>(subZones.size());
        Set<Map.Entry<String, Object>> entries = subZones.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            String key = entry.getKey();
            Map<String, Object> value = (Map<String, Object>) entry.getValue();

            zones.add(convert(key, value));
        }

        return zones;
    }

}
