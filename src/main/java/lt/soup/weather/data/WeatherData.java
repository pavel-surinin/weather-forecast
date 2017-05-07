package lt.soup.weather.data;

import java.util.Date;

/**
 * Created by Pavel on 2017-04-26.
 */
public class WeatherData {

    private Long id;
    private String city;
    private String country;
    private Date date;
    private WeatherGetType weatherGetType;
    private Level level;
    private Float temperature;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public WeatherGetType getWeatherGetType() {
        return weatherGetType;
    }

    public void setWeatherGetType(WeatherGetType weatherGetType) {
        this.weatherGetType = weatherGetType;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", date=" + date +
                ", weatherGetType=" + weatherGetType +
                ", level=" + level +
                ", temperature=" + temperature +
                '}';
    }
}


