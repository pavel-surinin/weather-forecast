package lt.soup.db;

import lt.soup.DateUtils;
import lt.soup.weather.data.WeatherData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Pavel on 2017-04-28.
 */
public class InMemoryDB implements Database {

    private List<WeatherData> data = new ArrayList<>();
    private AtomicLong idCounter = new AtomicLong();

    @Override
    public boolean save(WeatherData line) {
        if(isNewRecord(line)){
            line.setId(idCounter.addAndGet(1));
            data.add(line);
            return true;
        }
        return false;
    }

    @Override
    public List<WeatherData> findAll() {
        return data;
    }

    @Override
    public WeatherData findById(Long id) {
        WeatherData weatherData = data.stream().filter(d -> d.getId().equals(id)).findFirst().get();
        return weatherData;
    }

    @Override
    public void deleteAll() {
        data.clear();
    }

    private boolean isNewRecord(WeatherData line) {
        long foundRecords = data.stream()
                .filter(r -> r.getCity().equals(line.getCity()))
                .filter(r -> r.getCountry().equals(line.getCountry()))
                .filter(r -> DateUtils.getDateAsString(r.getDate()).equals(DateUtils.getDateAsString(line.getDate())))
                .filter(r -> r.getWeatherMinMax().equals(line.getWeatherMinMax()))
                .filter(r -> r.getWeatherGetType().equals(line.getWeatherGetType()))
                .count();
        return foundRecords == 0;
    }
}
