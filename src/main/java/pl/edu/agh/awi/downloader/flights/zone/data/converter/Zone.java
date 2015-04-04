package pl.edu.agh.awi.downloader.flights.zone.data.converter;

import java.util.List;

public class Zone {

    private final String name;

    private final double minimalLatitude;

    private final double maximalLatitude;

    private final double minimalLongitude;

    private final double maximalLongitude;

    private final List<Zone> subzones;

    public Zone(String name, double minimalLatitude, double maximalLatitude,
                double minimalLongitude, double maximalLongitude, List<Zone> subzones) {
        this.name = name;
        this.minimalLatitude = minimalLatitude;
        this.maximalLatitude = maximalLatitude;
        this.minimalLongitude = minimalLongitude;
        this.maximalLongitude = maximalLongitude;
        this.subzones = subzones;
    }

    public String getName() {
        return name;
    }

    public double getMinimalLatitude() {
        return minimalLatitude;
    }

    public double getMaximalLatitude() {
        return maximalLatitude;
    }

    public double getMinimalLongitude() {
        return minimalLongitude;
    }

    public double getMaximalLongitude() {
        return maximalLongitude;
    }

    public List<Zone> getSubzones() {
        return subzones;
    }
}
