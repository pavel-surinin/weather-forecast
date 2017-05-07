package lt.soup.web.resources;

import lt.soup.Actual;
import lt.soup.Forecast;
import lt.soup.LoggerUtils;
import lt.soup.SoupUtils;
import lt.soup.weather.data.Level;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

import java.util.stream.Stream;

/**
 * Created by Pavel on 2017-04-22.
 */
public class France implements WebResource {
    private static final int TODAY = 1;
    private static final String COUNTRY = "France";
    private static final String LINK = "http://www.vigilance-meteo.fr";
    private Logger logger = Logger.getLogger(France.class);
    private String city;
    private Document cityPage;

    public String getCountry() {
        return COUNTRY;
    }

    public Forecast getForecast(String city) {
        this.city = city;
        Forecast forecast = new Forecast(COUNTRY, city);
        cityPage = getCityPage();
        if (cityPage != null) {
            forecast.setDay3Max(get3DayMax());
            forecast.setDay3Min(get3DayMin());
            forecast.setDay7Min(get7Day(0));
            forecast.setDay7Max(get7Day(2));
            return forecast;
        } else {
            return null;
        }
    }

    @Override
    public Actual getActual(String city) {
        if (this.city == null) this.city = city;
        if (cityPage == null) this.cityPage = getCityPage();
        Actual actual = new Actual(COUNTRY, city);
        try {
            actual.setMinTemp(getTemperature(TODAY, Level.MIN));
            actual.setMaxTemp(getTemperature(TODAY, Level.MAX));
        } catch (Exception e) {
            LoggerUtils.logFailedScrap(logger,city);
            return null;
        }
        return actual;
    }

    /**
     * Method scraps city page and get min or max value for 7th day forecast
     *
     * @param num tag value is 6 / 16 °C, so value 0 for min, value 2 for max temp
     * @return temperature
     */
    private Float get7Day(int num) {
        String c = cityPage
                .getElementById("detail-table-forecast")
                .getElementsContainingOwnText("Température")
                .get(0)
                .parent()
                .children()
                .get(2)
                .text();
        String temperature = c.split(" ")[num];
        LoggerUtils.logTemperature(logger, COUNTRY, city, 7, Float.valueOf(temperature));
        return Float.valueOf(temperature);
    }

    private Float get3DayMax() {
        Integer temperature = cityPage
                .getElementById("detail-table-2")
                .getElementsByAttributeValue("title", "Température")
                .stream()
                .map(e -> Integer.valueOf(e.text().split(" ")[0]))
                .min((e1, e2) -> Integer.compare(e2, e1))
                .get();
        LoggerUtils.logTemperature(logger, COUNTRY, city, 3, Float.valueOf(temperature));
        return temperature.floatValue();
    }

    private Float get3DayMin() {
        Integer temperature = cityPage
                .getElementById("detail-table-2")
                .getElementsByAttributeValue("title", "Température")
                .stream()
                .map(e -> Integer.valueOf(e.text().split(" ")[0]))
                .min(Integer::compareTo)
                .get();
        LoggerUtils.logTemperature(logger, COUNTRY, city, 3, Float.valueOf(temperature));
        return temperature.floatValue();
    }

    /**
     * Gets temperature for 3days or actual weather
     *
     * @param day   today 1, 3 days forecast 3
     * @param level min or max temperature of the day from Level enum
     * @return temperature
     */
    private Float getTemperature(int day, Level level) throws Exception {
        Stream<Integer> daysTemperatures = cityPage
                .getElementById("detail-table-" + (day - 1))
                .getElementsByAttributeValue("title", "Température")
                .stream()
                .map(e -> Integer.valueOf(e.text().split(" ")[0]));
        Integer temperature;
        if (level.equals(Level.MAX)) {
            temperature = daysTemperatures.max(Integer::compareTo).get();
        } else {
            temperature = daysTemperatures.min(Integer::compareTo).get();
        }
        LoggerUtils.logTemperature(logger, COUNTRY, city, 3, (float) temperature);
        return temperature.floatValue();
    }

    private Document getCityPage() {
        try {
            String text = SoupUtils.getPage(LINK)
                    .getElementsContainingOwnText("Météo " + city)
                    .get(0)
                    .attr("href");
            return SoupUtils.getPage(LINK + text);
        } catch (Exception e) {
            LoggerUtils.logFailedScrap(logger, city);
            return null;
        }
    }


}
