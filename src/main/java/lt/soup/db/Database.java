package lt.soup.db;

import lt.soup.weather.data.WeatherData;

import java.util.List;

/**
 * Created by Pavel on 2017-04-27.
 */
public interface Database {

    boolean save(WeatherData line);

    List<WeatherData> findAll();

    WeatherData findById(Long id);

}
