package pl.edu.agh.awi.downloader.flights.flightDetails.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pl.edu.agh.awi.downloader.flights.flightDetails.CustomDateDeserializer;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightDetailsResponse {

    private final String fightId;

    private final String flightDesignator;

    private final String status;

    private final Date std;

    private final Date sta;

    private final Date atd;

    private final Date ata;

    private final Date eta;

    private final String destinationIata;

    private final String destinationCity;

    private final List<Double> destinationCoords;

    private final String destinationTimezoneCode;

    private final double destinationTimezoneOffset;

    private final String destinationTimezoneName;

    private final String originIata;

    private final String originCity;

    private final List<Double> originCoords;

    private final String originTimezoneCode;

    private final double originTimezoneOffset;

    private final String originTimezoneName;

    private final String airline;

    private final String aircraft;

    private final List<Double> trail;

    public FlightDetailsResponse(@JsonProperty("q") String fightId, @JsonProperty("flight") String flightDesignator,
                                 @JsonProperty("status") String status,
                                 @JsonProperty("dep_schd") @JsonDeserialize(using = CustomDateDeserializer.class) Date std,
                                 @JsonProperty("arr_schd") @JsonDeserialize(using = CustomDateDeserializer.class) Date sta,
                                 @JsonProperty("departure") @JsonDeserialize(using = CustomDateDeserializer.class) Date atd,
                                 @JsonProperty("arrival") @JsonDeserialize(using = CustomDateDeserializer.class) Date ata,
                                 @JsonProperty("eta") @JsonDeserialize(using = CustomDateDeserializer.class) Date eta,
                                 @JsonProperty("to_iata") String destinationIata, @JsonProperty("to_city") String destinationCity,
                                 @JsonProperty("to_pos") List<Double> destinationCoords, @JsonProperty("to_tz_code") String destinationTimezoneCode,
                                 @JsonProperty("to_tz_offset") double destinationTimezoneOffset, @JsonProperty("to_tz_name") String destinationTimezoneName,
                                 @JsonProperty("from_iata") String originIata, @JsonProperty("from_city") String originCity,
                                 @JsonProperty("from_pos") List<Double> originCoords, @JsonProperty("from_tz_code") String originTimezoneCode,
                                 @JsonProperty("from_tz_offset") double originTimezoneOffset, @JsonProperty("from_tz_name") String originTimezoneName,
                                 @JsonProperty("airline") String airline, @JsonProperty("aircraft") String aircraft,
                                 @JsonProperty("trail") List<Double> trail) {
        this.fightId = fightId;
        this.flightDesignator = flightDesignator;
        this.status = status;
        this.std = std;
        this.sta = sta;
        this.atd = atd;
        this.ata = ata;
        this.eta = eta;
        this.destinationIata = destinationIata;
        this.destinationCity = destinationCity;
        this.destinationCoords = destinationCoords;
        this.destinationTimezoneCode = destinationTimezoneCode;
        this.destinationTimezoneOffset = destinationTimezoneOffset;
        this.destinationTimezoneName = destinationTimezoneName;
        this.originIata = originIata;
        this.originCity = originCity;
        this.originCoords = originCoords;
        this.originTimezoneCode = originTimezoneCode;
        this.originTimezoneOffset = originTimezoneOffset;
        this.originTimezoneName = originTimezoneName;
        this.airline = airline;
        this.aircraft = aircraft;
        this.trail = trail;
    }

    public String getFightId() {
        return fightId;
    }

    public String getFlightDesignator() {
        return flightDesignator;
    }

    public String getStatus() {
        return status;
    }

    public Date getStd() {
        return std;
    }

    public Date getSta() {
        return sta;
    }

    public Date getAtd() {
        return atd;
    }

    public Date getAta() {
        return ata;
    }

    public Date getEta() {
        return eta;
    }

    public String getDestinationIata() {
        return destinationIata;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public List<Double> getDestinationCoords() {
        return destinationCoords;
    }

    public String getDestinationTimezoneCode() {
        return destinationTimezoneCode;
    }

    public double getDestinationTimezoneOffset() {
        return destinationTimezoneOffset;
    }

    public String getDestinationTimezoneName() {
        return destinationTimezoneName;
    }

    public String getOriginIata() {
        return originIata;
    }

    public String getOriginCity() {
        return originCity;
    }

    public List<Double> getOriginCoords() {
        return originCoords;
    }

    public String getOriginTimezoneCode() {
        return originTimezoneCode;
    }

    public double getOriginTimezoneOffset() {
        return originTimezoneOffset;
    }

    public String getOriginTimezoneName() {
        return originTimezoneName;
    }

    public String getAirline() {
        return airline;
    }

    public String getAircraft() {
        return aircraft;
    }

    public List<Double> getTrail() {
        return trail;
    }
}
