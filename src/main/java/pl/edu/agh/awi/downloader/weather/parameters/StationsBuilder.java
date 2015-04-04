package pl.edu.agh.awi.downloader.weather.parameters;

import java.util.HashSet;
import java.util.Set;

public class StationsBuilder {
    private Set<String> stations = new HashSet<>();

    public StationsBuilder addStation(String station) {
        this.stations.add(station);
        return this;
    }

    public Stations build() {
        return new Stations(stations);
    }
}