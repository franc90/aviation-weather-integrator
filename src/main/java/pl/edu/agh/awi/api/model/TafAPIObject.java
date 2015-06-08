package pl.edu.agh.awi.api.model;

import java.util.Date;

public class TafAPIObject extends AbstractWeatherAPIObject{

    private Date validFrom;
    private Date validTo;

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }


}
