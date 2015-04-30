package pl.edu.agh.awi.api.model;

public class TurbulenceConditionAPIObject {

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
}
