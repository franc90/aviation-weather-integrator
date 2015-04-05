package pl.edu.agh.awi.persistence.model;


import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Taf {

    @GraphId
    private Long id;

    private String tafInfo;

    public String getTafInfo() {
        return tafInfo;
    }

    public void setTafInfo(String tafInfo) {
        this.tafInfo = tafInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Taf taf = (Taf) o;

        return !(id != null ? !id.equals(taf.id) : taf.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
