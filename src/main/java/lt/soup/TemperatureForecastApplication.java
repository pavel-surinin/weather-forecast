package lt.soup;

import lt.soup.weather.data.WeatherData;
import lt.soup.weather.data.WeatherDataFactory;
import lt.soup.web.resources.WebResource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by Pavel on 2017-04-19.
 */
public class TemperatureForecastApplication {

    private final Logger logger = LogManager.getLogger(TemperatureForecastApplication.class);
    private static final String NAME = "Temperature Scrapper";
    private ArrayList<Forecast> forecasts = new ArrayList<>();


    public TemperatureForecastApplication start() {
        logger.info("Application Temperature Forecast is started");
        return this;
    }

    public TemperatureForecastApplication scrap(WebResource resource, String... cities) {
        for (String city : cities) {
            logger.info("Scrapping " + resource.getCountry() + " in " + city + " with " + NAME);
            forecasts.add(resource.getForecast(city));
        }
        return this;
    }


    public ArrayList<Forecast> getForecasts() {
        return forecasts;
    }

    public ArrayList<WeatherData> getWeatherDataList(){
        return new WeatherDataFactory(forecasts).getDataList();
    }
}

