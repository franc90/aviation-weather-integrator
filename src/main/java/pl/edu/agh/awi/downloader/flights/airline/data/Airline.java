package pl.edu.agh.awi.downloader.flights.airline.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Airline {

    private final String name;

    private final String iata;

    private final String icao;

    public Airline(@JsonProperty("Name")String name, @JsonProperty("Code")String iata, @JsonProperty("ICAO")String icao) {
        this.name = name;
        this.iata = iata;
        this.icao = icao;
    }

    public String getName() {
        return name;
    }

    public String getIata() {
        return iata;
    }

    public String getIcao() {
        return icao;
    }
}
