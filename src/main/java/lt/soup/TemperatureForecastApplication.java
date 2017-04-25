package lt.soup;

import lt.soup.web.resources.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import rx.Observable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by Pavel on 2017-04-19.
 */
public class TemperatureForecastApplication {

    private final Logger logger = LogManager.getLogger(TemperatureForecastApplication.class);
    private final String NAME = "Temperature Scrapper";

    public void start() {
        logger.info("Application Temperature Forecast is started");

        getTemperature(new England(), "London","Glasgow");
        getTemperature(new Lithuania(), "Vilnius","Klaipeda","Kaunas");
        getTemperature(new France(), "Paris","Lille");
        getTemperature(new Poland(), "Poznan","Warsaw");
    }

    private Forecast getTemperature(WebResource resource, String... cities) {
        logger.info("Scrapping " + resource.getCountry() + " in " + city + " with " + NAME);
            return resource.getForecast(city);
    }
}

