package lt.soup;

import java.util.Date;

/**
 * Created by Pavel on 2017-04-19.
 */
public class Forecast {

    private String city;
    private String webResource;

    private Float day3Max;
    private Float day3Min;
    private Float day7Max;
    private Float day7Min;
    private Date date;

    public Forecast(String webResource, String city) {
        this.webResource = webResource;
        this.city = city;
        this.date = new Date();
    }

    public Float getDay3Max() {
        return day3Max;
    }

    public void setDay3Max(Float day3Max) {
        this.day3Max = day3Max;
    }

    public Float getDay3Min() {
        return day3Min;
    }

    public void setDay3Min(Float day3Min) {
        this.day3Min = day3Min;
    }

    public Float getDay7Max() {
        return day7Max;
    }

    public void setDay7Max(Float day7Max) {
        this.day7Max = day7Max;
    }

    public Float getDay7Min() {
        return day7Min;
    }

    public void setDay7Min(Float day7Min) {
        this.day7Min = day7Min;
    }

    public Date getDateScrapped() {
        return this.date;
    }
}
