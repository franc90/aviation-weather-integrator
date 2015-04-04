package pl.edu.agh.awi.downloader.flights.zone.data;

import java.util.List;

public class ZoneResponse {

    private final long version;

    private final List<Zone> zones;

    public ZoneResponse(long version, List<Zone> zones) {
        this.version = version;
        this.zones = zones;
    }

    public long getVersion() {
        return version;
    }

    public List<Zone> getZones() {
        return zones;
    }
}
