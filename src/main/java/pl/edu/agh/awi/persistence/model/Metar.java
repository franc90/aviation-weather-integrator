package pl.edu.agh.awi.persistence.model;


import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

import java.util.Date;

@NodeEntity
public class Metar {

    @GraphId
    private Long id;

    private Date timeStamp = new Date();
    private Double temperature;
    private Double presure;
    private String visibility;
    private Double dewPointTemperature;
    private Double windSpeed;
    private Double windForce;
    private String cloudbase;
    private String percipation;
    private String skyCondition;

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getPresure() {
        return presure;
    }

    public void setPresure(Double presure) {
        this.presure = presure;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Double getDewPointTemperature() {
        return dewPointTemperature;
    }

    public void setDewPointTemperature(Double dewPointTemperature) {
        this.dewPointTemperature = dewPointTemperature;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Double getWindForce() {
        return windForce;
    }

    public void setWindForce(Double windForce) {
        this.windForce = windForce;
    }

    public String getCloudbase() {
        return cloudbase;
    }

    public void setCloudbase(String cloudbase) {
        this.cloudbase = cloudbase;
    }

    public String getPercipation() {
        return percipation;
    }

    public void setPercipation(String percipation) {
        this.percipation = percipation;
    }

    public String getSkyCondition() {
        return skyCondition;
    }

    public void setSkyCondition(String skyCondition) {
        this.skyCondition = skyCondition;
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
