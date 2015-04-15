package pl.edu.agh.awi.persistence.model;

import com.google.common.collect.Sets;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.*;
import pl.edu.agh.awi.persistence.model.weather_condition.AirSigmet;
import pl.edu.agh.awi.persistence.model.weather_condition.Metar;
import pl.edu.agh.awi.persistence.model.weather_condition.Taf;
import pl.edu.agh.awi.persistence.model.weather_condition.AbstractWeatherCondition;

import java.util.Date;
import java.util.Set;

@NodeEntity
public class AirPort {

    @GraphId
    private Long id;

    @Indexed
    private String name;

    private String city;

    private String country;

    private Integer numberOfRunways;

    private Double longitude;

    private Double latitude;

    @Indexed
    private String iataCode;

    @Indexed
    private String icaoCode;

    @RelatedTo(type = "metar")
    private Set<Metar> metars = Sets.newHashSet();

    @RelatedTo(type = "taf")
    private Set<Taf> tafs = Sets.newHashSet();

    @RelatedTo(type = "airsigmet")
    private Set<AirSigmet> airSigmets = Sets.newHashSet();

    @RelatedTo(type = "from")
    private Set<Flight> outgoingFlights = Sets.newHashSet();

    @RelatedTo(type = "to", direction = Direction.INCOMING)
    private Set<Flight> incomingFlights = Sets.newHashSet();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getNumberOfRunways() {
        return numberOfRunways;
    }

    public void setNumberOfRunways(Integer numberOfRunways) {
        this.numberOfRunways = numberOfRunways;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public String getIcaoCode() {
        return icaoCode;
    }

    public void setIcaoCode(String icaoCode) {
        this.icaoCode = icaoCode;
    }

    public Set<Metar> getMetars() {
        return metars;
    }

    public Set<Taf> getTafs() {
        return tafs;
    }

    public Set<AirSigmet> getAirSigmets() {
        return airSigmets;
    }

    public Set<Flight> getOutgoingFlights() {
        return outgoingFlights;
    }

    public Set<Flight> getIncomingFlights() {
        return incomingFlights;
    }

    public void addMetar(Metar metar) {
        metars.add(metar);
    }

    public void addTaf(Taf taf) {
        tafs.add(taf);
    }

    public void addAirSigmet(AirSigmet airSigmet) {
        airSigmets.add(airSigmet);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AirPort airPort = (AirPort) o;

        return !(id != null ? !id.equals(airPort.id) : airPort.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
