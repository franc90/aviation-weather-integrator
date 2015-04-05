package pl.edu.agh.awi.persistence.model;

import com.google.common.collect.Sets;
import org.springframework.data.neo4j.annotation.*;

import java.util.Set;

@NodeEntity
public class AirPort {

    @GraphId
    private Long id;

    @Indexed(unique = true)
    private String name;

    @RelatedTo(type = "weather_infos")
    @Fetch
    private Set<WeatherInfo> weatherInfo = Sets.newHashSet();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<WeatherInfo> getWeatherInfo() {
        return weatherInfo;
    }

    public void addWeatherInfo(WeatherInfo info) {
        weatherInfo.add(info);
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
