package lt.soup.web.resources;

import lt.soup.*;
import lt.soup.weather.data.Level;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;

import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by Pavel on 2017-04-22.
 */
public class Lithuania implements WebResource {
    private static final String COUNTRY = "Lithuania";
    private static final String LINK = "http://www.meteo.lt";
    private Logger logger = Logger.getLogger(Lithuania.class);
    private final String SUFFIX = "/lt_LT/miestas?placeCode=";
    private final String MAX = "Diena";
    private final String MIN = "Naktis";
    private String city;

    public String getCountry() {
        return COUNTRY;
    }

    public Forecast getForecast(String city) {
        this.city = city;
        Forecast forecast = new Forecast(COUNTRY, city);
        try {
            forecast.setDay3Max(getTemperature(2, MAX));
            forecast.setDay3Min(getTemperature(2, MIN));
            forecast.setDay7Max(getTemperature(6, MAX));
            forecast.setDay7Min(getTemperature(6, MIN));
        } catch (Exception e) {
            LoggerUtils.logFailedScrap(logger, city);
            return null;
        }
        return forecast;
    }

    @Override
    public Actual getActual(String city) {
        this.city = city;
        Actual actual = new Actual(COUNTRY, city);
        try {
            actual.setMaxTemp(getActualTemp(Level.MAX));
            actual.setMinTemp(getActualTemp(Level.MIN));
            return actual;
        } catch (Exception e) {
            LoggerUtils.logFailedScrap(logger, city);
            return null;
        }
    }

    private Float getActualTemp(Level level) {
        IntStream intStream = SoupUtils.getPage(LINK + SUFFIX + city)
                .getElementsByClass(DateUtils.getDateAddForLt(0))
                .stream()
                .filter(element -> element.classNames().contains("hourly_weather"))
                .findFirst()
                .get()
                .getElementsByAttributeValue("class", "temperature")
                .stream()

                .mapToInt(e -> Integer.valueOf(e.text().split(" ")[0]));
        if (level.equals(Level.MIN)) return (float) intStream.min().getAsInt();
        if (level.equals(Level.MAX)) return (float) intStream.max().getAsInt();

    return null;
    }

    private Float getTemperature(int days, String daytime) throws Exception {
        String date = DateUtils.getDateAddForLt(days);
        String html = SoupUtils.getPage(LINK + SUFFIX + city)
                .getElementsContainingOwnText(date)
                .get(0)
                .parent()
                .getElementsByClass("col")
                .stream()
                .filter(e -> e.getElementsContainingText(daytime).size() > 0)
                .findFirst()
                .get()
                .getElementsByClass("big").html();
        String temperature = html.split(" ")[0];
        LoggerUtils.logTemperature(logger, COUNTRY, city, (days + 1), Float.valueOf(temperature));
        return Float.parseFloat(temperature);
    }

}
