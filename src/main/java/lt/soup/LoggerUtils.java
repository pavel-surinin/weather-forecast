package lt.soup;

import org.apache.log4j.Logger;

/**
 * Created by Pavel on 2017-04-22.
 */
public class LoggerUtils {

    /**
     * Logging, failed to scrap, info
     *
     * @param logger    logger of the class where scrapping failed
     * @param city      city that is failed to scrap
     */
    public static void logFailedScrap(Logger logger, String city){
        logger.warn("Unable to get info for " + city + " city");
    }

    /**
     * Logging result if scrapping city temperature
     *
     * @param logger    logger of the class where scrapping is processing
     * @param COUNTRY   country is being scrapped
     * @param city      city in this country
     * @param days      number of days in forecast
     * @param temperature
     */
    public static void logTemperature(Logger logger, String COUNTRY, String city, int days, float temperature){
        logger.debug(COUNTRY + ", " + city + " " + days + " days, temp: " + temperature + "°C");

    }

}
