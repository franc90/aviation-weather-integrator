package pl.edu.agh.awi.persistence.model;


import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Metar {

    @GraphId
    private Long id;

    private String metarInfo;

    public String getMetarInfo() {
        return metarInfo;
    }

    public void setMetarInfo(String metarInfo) {
        this.metarInfo = metarInfo;
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
