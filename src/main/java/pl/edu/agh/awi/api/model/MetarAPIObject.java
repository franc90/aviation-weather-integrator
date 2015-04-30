package pl.edu.agh.awi.api.model;

import java.util.Date;

public class MetarAPIObject extends AbstractWeatherAPIObject {

    private Date timestamp;


    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }


}
