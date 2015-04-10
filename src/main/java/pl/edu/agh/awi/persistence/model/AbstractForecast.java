package pl.edu.agh.awi.persistence.model;

import org.springframework.data.neo4j.annotation.GraphId;

import java.util.Date;


public abstract class AbstractForecast {

    @GraphId
    protected Long id;
    private Date timestamp = new Date();
    private Double temperature;
    private Double pressure;
    private String visibility;
    private Double dewPointTemperature;
    private Double windSpeed;
    private Double windForce;
    private String windDirection;
    private String cloudbase;
    private Double cloudHeight;
    private String percipation;
    private String skyCondition;
    private String turbulenceInfo;
    private String infoType;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
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

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public Double getCloudHeight() {
        return cloudHeight;
    }

    public void setCloudHeight(Double cloudHeight) {
        this.cloudHeight = cloudHeight;
    }

    public String getTurbulenceInfo() {
        return turbulenceInfo;
    }

    public void setTurbulenceInfo(String turbulenceInfo) {
        this.turbulenceInfo = turbulenceInfo;
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }
}
