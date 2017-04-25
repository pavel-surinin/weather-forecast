package lt.soup;

import lt.soup.web.resources.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by Pavel on 2017-04-19.
 */
public class TemperatureForecastApplication {

    private final Logger logger = LogManager.getLogger(TemperatureForecastApplication.class);
    private final String NAME = "Temperature Scrapper";

    public void start() {
        logger.info("Application Temperature Forecast is started");

        getTemperature(new England(), "London", "Glasgow");
        getTemperature(new Lithuania(), "Vilnius", "Klaipeda", "Kaunas");
        getTemperature(new France(), "Paris", "Lille");
        getTemperature(new Poland(), "Poznan", "Warsaw");

    }

    private ArrayList<Forecast> getTemperature(WebResource resource, String... cities) {
        ArrayList<Forecast> forecasts = new ArrayList<>();
        for (String city : cities) {
            forecasts.add(resource.getForecast(city));
            logger.info("Scrapping " + resource.getCountry() + " in " + city + " with " + NAME);
        }
        return forecasts;
    }
}

