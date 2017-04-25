package lt.soup.web.resources;

import lt.soup.Forecast;
import lt.soup.LoggerUtils;
import lt.soup.SoupUtils;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

/**
 * Created by Pavel on 2017-04-22.
 */
public class England implements WebResource {
    private static final String COUNTRY = "England";
    private static final String LINK = "http://www.metoffice.gov.uk";
    Logger logger = Logger.getLogger(England.class);
    private String city;

    @Override
    public String getCountry() {
        return COUNTRY;
    }

    @Override
    public Forecast getForecast(String city) {
        this.city = city;
        Document cityWeatherPage = getCityWeatherPage();
        if (cityWeatherPage != null) {
            Forecast forecast = new Forecast(COUNTRY, city);
            forecast.setDay3Max(getDay3Max(cityWeatherPage));
            forecast.setDay3Min(getDay3Min(cityWeatherPage));
            forecast.setDay7Max(getDay7Max(cityWeatherPage));
            forecast.setDay7Min(getDay7Min(cityWeatherPage));

            return forecast;
        } else {
            return null;
        }
    }

    private Float getDay7Min(Document cityWeatherPage) {
        String attr = cityWeatherPage
                .getElementById("tabDay6")
                .getElementsByClass("tabDayValues")
                .get(0)
                .getElementsByAttributeValue("title", "Minimum nighttime temperature")
                .attr("data-value-raw");
        LoggerUtils.logTemperature(logger,COUNTRY,city,7,Float.valueOf(attr));
        return Float.parseFloat(attr);
    }

    private Float getDay7Max(Document cityWeatherPage) {
        String attr = cityWeatherPage
                .getElementById("tabDay6")
                .getElementsByClass("tabDayValues")
                .get(0)
                .getElementsByAttributeValue("title", "Maximum daytime temperature")
                .attr("data-value-raw");
        LoggerUtils.logTemperature(logger,COUNTRY,city,7,Float.valueOf(attr));
        return Float.parseFloat(attr);
    }

    private float getDay3Max(Document cityWeatherPage) {
        String attr = cityWeatherPage
                .getElementById("tabDay2")
                .getElementsByClass("tabDayValues")
                .get(0)
                .getElementsByAttributeValue("title", "Maximum daytime temperature")
                .attr("data-value-raw");
        LoggerUtils.logTemperature(logger,COUNTRY,city,3,Float.valueOf(attr));
        return Float.parseFloat(attr);
    }

    private Float getDay3Min(Document cityWeatherPage) {
        String attr = cityWeatherPage
                .getElementById("tabDay2")
                .getElementsByClass("tabDayValues")
                .get(0)
                .getElementsByAttributeValue("title", "Minimum nighttime temperature")
                .attr("data-value-raw");
        LoggerUtils.logTemperature(logger,COUNTRY,city,3,Float.valueOf(attr));
        return Float.parseFloat(attr);
    }

    private Document getCityWeatherPage() {
        try {
            String linkToCity = SoupUtils.getPage(LINK)
                    .getElementsContainingOwnText(city + " weather")
                    .stream()
                    .filter(e -> e.hasAttr("href"))
                    .findFirst()
                    .get()
                    .attr("href");
            return SoupUtils.getPage(LINK + linkToCity);

        } catch (Exception e) {
            LoggerUtils.logFailedScrap(logger, city);
            return null;
        }
    }
}
