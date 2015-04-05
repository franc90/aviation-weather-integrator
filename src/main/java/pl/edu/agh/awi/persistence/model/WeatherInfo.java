package pl.edu.agh.awi.persistence.model;

import com.google.common.collect.Sets;
import org.springframework.data.neo4j.annotation.*;

import java.util.Date;
import java.util.Set;

@NodeEntity
public class WeatherInfo {

    @GraphId
    private Long id;

    private Date timestamp = new Date();

    @RelatedTo(type = "tafs")
    @Fetch
    private Set<Taf> taf = Sets.newHashSet();

    @RelatedTo(type = "metars")
    @Fetch
    private Set<Metar> metar = Sets.newHashSet();

    public Set<Taf> getTaf() {
        return taf;
    }

    public Set<Metar> getMetar() {
        return metar;
    }

    public void addTaf(Taf taf) {
        this.taf.add(taf);
    }


    public void addMetar(Metar metar) {
        this.metar.add(metar);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeatherInfo info = (WeatherInfo) o;

        return !(id != null ? !id.equals(info.id) : info.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
