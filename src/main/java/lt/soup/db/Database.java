package lt.soup.db;

import lt.soup.weather.data.WeatherData;

/**
 * Created by Pavel on 2017-04-27.
 */
public interface Database {

    void save();

    WeatherData findAll();

    WeatherData findById(Long id);

}
