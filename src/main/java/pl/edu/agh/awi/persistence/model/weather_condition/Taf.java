package pl.edu.agh.awi.persistence.model.weather_condition;

import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Taf extends AbstractWeatherCondition {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Taf metar = (Taf) o;

        return !(id != null ? !id.equals(metar.id) : metar.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
