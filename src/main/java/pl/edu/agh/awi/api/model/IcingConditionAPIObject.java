package pl.edu.agh.awi.api.model;

public class IcingConditionAPIObject {

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
}
