package pl.edu.agh.awi.persistence.model.weather_condition;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class SkyCondition {

    @GraphId
    private Long id;
    private String skyCover;
    private Integer cloudBase;

    public String getSkyCover() {
        return skyCover;
    }

    public void setSkyCover(String skyCover) {
        this.skyCover = skyCover;
    }

    public Integer getCloudBase() {
        return cloudBase;
    }

    public void setCloudBase(Integer cloudBase) {
        this.cloudBase = cloudBase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SkyCondition that = (SkyCondition) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
