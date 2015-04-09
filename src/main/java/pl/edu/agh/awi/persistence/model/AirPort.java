package pl.edu.agh.awi.persistence.model;

import com.google.common.collect.Sets;
import org.springframework.data.neo4j.annotation.*;

import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

@NodeEntity
public class AirPort {

    @GraphId
    private Long id;

    @Indexed(unique = true)
    private String name;

    private String city;

    private String country;

    private Integer numberOfRunways;

    private Double longitude;

    private Double latitude;

    private String iataCode;

    private String icaoCode;

    @RelatedTo(type = "metars")
    @Fetch
    private Set<Metar> metars = Sets.newHashSet();

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

    public void addMetar(Metar metar) {
        metars.add(metar);
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
