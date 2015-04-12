package pl.edu.agh.awi.persistence.model.weather_condition;


import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

import java.util.Date;

@NodeEntity
public class Metar extends AbstractWeatherCondition {

    @GraphId
    private Long id;

    private Date timestamp = new Date();

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Metar metar = (Metar) o;

        return !(id != null ? !id.equals(metar.id) : metar.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
