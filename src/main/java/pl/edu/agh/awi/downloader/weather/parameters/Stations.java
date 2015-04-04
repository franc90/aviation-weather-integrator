package pl.edu.agh.awi.downloader.weather.parameters;

import java.util.Set;

public class Stations {

    private final Set<String> stations;

    public Stations(Set<String> stations) {
        this.stations = stations;
    }

    public String getStations() {
        return String.join("%20", stations);
    }

}
