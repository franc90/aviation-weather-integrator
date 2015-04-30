package pl.edu.agh.awi.api.model;

import java.util.Set;

public class AbstractWeatherAPIObject {

    private Double temperature;
    private Double pressure;
    private Double dewPointTemperature;
    private Double windSpeed;
    private Double windGust;
    private String windDirection;
    private String percipation;
    private String infoType;
    private Double visibilityStatute;
    private Integer verticalVisibility;

    private Set<SkyConditionAPIObject> skyConditions;
    private Set<TurbulenceConditionAPIObject> turbulenceConditions;
    private Set<IcingConditionAPIObject> icingConditions;

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

    public Double getWindGust() {
        return windGust;
    }

    public void setWindGust(Double windGust) {
        this.windGust = windGust;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getPercipation() {
        return percipation;
    }

    public void setPercipation(String percipation) {
        this.percipation = percipation;
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public Double getVisibilityStatute() {
        return visibilityStatute;
    }

    public void setVisibilityStatute(Double visibilityStatute) {
        this.visibilityStatute = visibilityStatute;
    }

    public Integer getVerticalVisibility() {
        return verticalVisibility;
    }

    public void setVerticalVisibility(Integer verticalVisibility) {
        this.verticalVisibility = verticalVisibility;
    }

    public Set<SkyConditionAPIObject> getSkyConditions() {
        return skyConditions;
    }

    public void setSkyConditions(Set<SkyConditionAPIObject> skyConditions) {
        this.skyConditions = skyConditions;
    }

    public Set<TurbulenceConditionAPIObject> getTurbulenceConditions() {
        return turbulenceConditions;
    }

    public void setTurbulenceConditions(Set<TurbulenceConditionAPIObject> turbulenceConditions) {
        this.turbulenceConditions = turbulenceConditions;
    }

    public Set<IcingConditionAPIObject> getIcingConditions() {
        return icingConditions;
    }

    public void setIcingConditions(Set<IcingConditionAPIObject> icingConditions) {
        this.icingConditions = icingConditions;
    }
}
