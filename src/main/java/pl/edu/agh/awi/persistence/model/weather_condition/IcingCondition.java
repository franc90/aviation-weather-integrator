package pl.edu.agh.awi.persistence.model.weather_condition;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class IcingCondition {

    @GraphId
    private Long id;
    private String icingIntensity;
    private Integer icingMinAltitude;
    private Integer icingMaxAltitude;

    public String getIcingIntensity() {
        return icingIntensity;
    }

    public void setIcingIntensity(String icingIntensity) {
        this.icingIntensity = icingIntensity;
    }

    public Integer getIcingMinAltitude() {
        return icingMinAltitude;
    }

    public void setIcingMinAltitude(Integer icingMinAltitude) {
        this.icingMinAltitude = icingMinAltitude;
    }

    public Integer getIcingMaxAltitude() {
        return icingMaxAltitude;
    }

    public void setIcingMaxAltitude(Integer icingMaxAltitude) {
        this.icingMaxAltitude = icingMaxAltitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IcingCondition that = (IcingCondition) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
