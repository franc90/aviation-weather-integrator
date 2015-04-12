package pl.edu.agh.awi.persistence.model.weather_condition;

import com.google.common.collect.Sets;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Set;


public abstract class AbstractWeatherCondition {

    private Double temperature;
    private Double pressure;
    private Double dewPointTemperature;
    private Double windSpeed;
    private Double windGust;
    private String windDirection;
    private Double cloudHeight;
    private String percipation;
    private String infoType;
    private Double visibilityStatute;
    private Integer verticalVisibility;

    @RelatedTo(type = "sky_condition")
    private Set<SkyCondition> skyConditions = Sets.newHashSet();

    @RelatedTo(type = "turbulence_condition")
    private Set<TurbulenceCondition> turbulenceConditions = Sets.newHashSet();

    @RelatedTo(type = "icing_condition")
    private Set<IcingCondition> icingConditions = Sets.newHashSet();

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

    public String getPercipation() {
        return percipation;
    }

    public void setPercipation(String percipation) {
        this.percipation = percipation;
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

    public Set<SkyCondition> getSkyConditions() {
        return skyConditions;
    }

    public void addSkyCondition(SkyCondition skyCondition) {
        skyConditions.add(skyCondition);
    }

    public Set<TurbulenceCondition> getTurbulenceConditions() {
        return turbulenceConditions;
    }

    public void addTurbulenceCondition(TurbulenceCondition turbulenceCondition) {
        turbulenceConditions.add(turbulenceCondition);
    }

    public Set<IcingCondition> getIcingConditions() {
        return icingConditions;
    }

    public void addIcingCondition(IcingCondition icingCondition) {
        icingConditions.add(icingCondition);
    }

    public interface WeatherConditionFunction<T> {
        T withWeatherCondition(AbstractWeatherCondition condition);
    }

}
