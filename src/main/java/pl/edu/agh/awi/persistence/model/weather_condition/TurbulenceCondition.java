package pl.edu.agh.awi.persistence.model.weather_condition;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class TurbulenceCondition {

    @GraphId
    private Long id;
    private String turbulenceIntensity;
    private Integer turbulenceMinAltitude;
    private Integer turbulenceMaxAltitude;

    public String getTurbulenceIntensity() {
        return turbulenceIntensity;
    }

    public void setTurbulenceIntensity(String turbulenceIntensity) {
        this.turbulenceIntensity = turbulenceIntensity;
    }

    public Integer getTurbulenceMinAltitude() {
        return turbulenceMinAltitude;
    }

    public void setTurbulenceMinAltitude(Integer turbulenceMinAltitude) {
        this.turbulenceMinAltitude = turbulenceMinAltitude;
    }

    public Integer getTurbulenceMaxAltitude() {
        return turbulenceMaxAltitude;
    }

    public void setTurbulenceMaxAltitude(Integer turbulenceMaxAltitude) {
        this.turbulenceMaxAltitude = turbulenceMaxAltitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TurbulenceCondition that = (TurbulenceCondition) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
