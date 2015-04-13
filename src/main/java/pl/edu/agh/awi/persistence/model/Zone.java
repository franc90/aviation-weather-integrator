package pl.edu.agh.awi.persistence.model;

import com.google.common.collect.Sets;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Set;

@NodeEntity
public class Zone {

    @GraphId
    private Long id;

    private  String name;

    private  Double minimalLatitude;

    private  Double maximalLatitude;

    private  Double minimalLongitude;

    private  Double maximalLongitude;

    @RelatedTo(type = "subzone")
    private Set<Zone> subzones = Sets.newHashSet();

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

}
