package lt.soup;

import lt.soup.weather.data.Level;
import lt.soup.weather.data.WeatherData;
import lt.soup.weather.data.WeatherGetType;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Pavel on 2017-05-05.
 */
public class Actual {
    private final String country;
    private final String city;
    private final Date date;
    private Float minTemp;
    private Float maxTemp;

    public Actual(String country, String city) {
        this.country = country;
        this.city = city;
        this.date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public Float getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(Float minTemp) {
        this.minTemp = minTemp;
    }

    public Float getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(Float maxTemp) {
        this.maxTemp = maxTemp;
    }

    public List<WeatherData> getWeatherDatas() {
        return Stream.of(minTemp, maxTemp)
                .map(t -> {
                    WeatherData weatherData = new WeatherData();
                    weatherData.setCountry(country);
                    weatherData.setCity(city);
                    weatherData.setDate(date);
                    weatherData.setWeatherGetType(WeatherGetType.ACTUAL);
                    weatherData.setTemperature(t);
                    if (t.equals(minTemp)) {
                        weatherData.setLevel(Level.MIN);
                    } else {
                        weatherData.setLevel(Level.MAX);
                    }
                    return weatherData;
                })
                .collect(Collectors.toList());
    }
}
