package pl.edu.agh.awi.persistence.model;

import com.google.common.collect.Sets;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.io.Serializable;
import java.util.Set;

@NodeEntity
public class Zone implements Serializable {

    @GraphId
    private Long id;

    private  String name;

    private  Double minimalLatitude;

    private  Double maximalLatitude;

    private  Double minimalLongitude;

    private  Double maximalLongitude;

    @RelatedTo(type = "subzone")
    private Set<Zone> subzones = Sets.newHashSet();

    public Zone() {
    }

    private Zone(String name, Double minimalLatitude, Double maximalLatitude, Double minimalLongitude, Double maximalLongitude) {
        this.name = name;
        this.minimalLatitude = minimalLatitude;
        this.maximalLatitude = maximalLatitude;
        this.minimalLongitude = minimalLongitude;
        this.maximalLongitude = maximalLongitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMinimalLatitude() {
        return minimalLatitude;
    }

    public void setMinimalLatitude(Double minimalLatitude) {
        this.minimalLatitude = minimalLatitude;
    }

    public Double getMaximalLatitude() {
        return maximalLatitude;
    }

    public void setMaximalLatitude(Double maximalLatitude) {
        this.maximalLatitude = maximalLatitude;
    }

    public Double getMinimalLongitude() {
        return minimalLongitude;
    }

    public void setMinimalLongitude(Double minimalLongitude) {
        this.minimalLongitude = minimalLongitude;
    }

    public Double getMaximalLongitude() {
        return maximalLongitude;
    }

    public void setMaximalLongitude(Double maximalLongitude) {
        this.maximalLongitude = maximalLongitude;
    }

    public Set<Zone> getSubzones() {
        return subzones;
    }

    public void addSubzone(Zone subzone) {
        subzones.add(subzone);
    }

    public static class Builder  {
        private  String name;
        private  Double minimalLatitude;
        private  Double maximalLatitude;
        private  Double minimalLongitude;
        private  Double maximalLongitude;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withLatitude(Double min, Double max) {
            this.minimalLatitude = min;
            this.maximalLatitude = max;
            return this;
        }

        public Builder withLongitude(Double min, Double max) {
            this.minimalLongitude = min;
            this.maximalLongitude = max;
            return this;
        }

        public Zone build() {
            return new Zone(name, minimalLatitude, maximalLatitude, minimalLongitude, maximalLongitude);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Zone zone = (Zone) o;

        return !(id != null ? !id.equals(zone.id) : zone.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
