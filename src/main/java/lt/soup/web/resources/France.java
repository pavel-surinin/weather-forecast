package lt.soup.web.resources;

import lt.soup.Forecast;
import lt.soup.LoggerUtils;
import lt.soup.SoupUtils;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

/**
 * Created by Pavel on 2017-04-22.
 */
public class France implements WebResource {
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
            forecast.setDay3Max(get3DayMin());
            forecast.setDay3Min(get3DayMax());
            forecast.setDay7Min(get7Day(0));
            forecast.setDay7Max(get7Day(2));
            return forecast;
        } else {
            return null;
        }
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
                .getElementsContainingOwnText("C")
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
