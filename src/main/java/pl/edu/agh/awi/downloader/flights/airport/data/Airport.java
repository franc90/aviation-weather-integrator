package pl.edu.agh.awi.downloader.flights.airport.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Airport {

    private final String name;

    private final String iata;

    private final String icao;

    private final float latitude;

    private final float longitude;

    private final String country;

    private final int altitude;

    public Airport(@JsonProperty("name") String name, @JsonProperty("iata") String iata, @JsonProperty("icao") String icao,
                   @JsonProperty("lat") float latitude, @JsonProperty("lon") float longitude, @JsonProperty("country") String country,
                   @JsonProperty("alt") int altitude) {
        this.name = name;
        this.iata = iata;
        this.icao = icao;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.altitude = altitude;
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

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public int getAltitude() {
        return altitude;
    }
}
