package pl.edu.agh.awi.api.model;

public class SkyConditionAPIObject {

    private String skyCover;
    private Integer cloudBase;

    public String getSkyCover() {
        return skyCover;
    }

    public void setSkyCover(String skyCover) {
        this.skyCover = skyCover;
    }

    public Integer getCloudBase() {
        return cloudBase;
    }

    public void setCloudBase(Integer cloudBase) {
        this.cloudBase = cloudBase;
    }
}
