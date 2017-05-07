package lt.soup;

import lt.soup.analytics.AnalyticsReport;
import lt.soup.analytics.Analyzer;
import lt.soup.db.Database;
import lt.soup.weather.data.WeatherData;
import lt.soup.weather.data.WeatherDataFactory;
import lt.soup.web.resources.WebResource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel on 2017-04-19.
 */
public class TemperatureForecastApplication {

    private final Logger logger = LogManager.getLogger(TemperatureForecastApplication.class);
    private static final String NAME = "Temperature Scrapper";
    private ArrayList<Forecast> forecasts = new ArrayList<>();
    private ArrayList<WeatherData> actual = new ArrayList<>();
    private Database database;


    public TemperatureForecastApplication start() {
        logger.info("Application Temperature Forecast is started");
        return this;
    }

    public TemperatureForecastApplication scrap(WebResource resource, String... cities) {
        for (String city : cities) {
            logger.info("Scrapping " + resource.getCountry() + " in " + city + " with " + NAME);
            Forecast forecast = resource.getForecast(city);
            Actual actual = resource.getActual(city);
            if (actual != null) this.actual.addAll(actual.getWeatherDatas());
            if (forecast != null) forecasts.add(forecast);
        }
        return this;
    }

    public TemperatureForecastApplication setDatabase(Database database) {
        this.database = database;
        return this;
    }

    public TemperatureForecastApplication saveToDatabase() {
        getWeatherDataList().forEach(database::save);
        actual.forEach(database::save);
        return this;
    }

    public ArrayList<Forecast> getForecasts() {
        return forecasts;
    }

    public ArrayList<WeatherData> getWeatherDataList() {
        return new WeatherDataFactory(forecasts).getDataList();
    }

    public List<AnalyticsReport> analize() {
        if (database.findAll().isEmpty()) {
            List<WeatherData> weatherDataList = new ArrayList<>();
            weatherDataList.addAll(new WeatherDataFactory(forecasts).getDataList());
            weatherDataList.addAll(actual);
            return Analyzer.analyze(weatherDataList);
        } else {
            return Analyzer.analyze(database.findAll());
        }
    }
}

