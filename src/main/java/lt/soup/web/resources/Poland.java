package lt.soup.web.resources;

import lt.soup.DateUtils;
import lt.soup.Forecast;
import lt.soup.LoggerUtils;
import lt.soup.SoupUtils;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

import java.util.Collections;

/**
 * Created by Pavel on 2017-04-23.
 */
public class Poland implements WebResource {
    private static final String COUNTRY = "Poland";
    private static final String LINK = "https://www.meteoprog.pl/";
    private Logger logger = Logger.getLogger(Poland.class);
    private String city;
    private Document cityPage;
    private final String MIN = "from";
    private final String MAX = "to";

    public String getCountry() {
        return COUNTRY;
    }

    public Forecast getForecast(String city) {
        this.city = city;
        try {
        setCityPage();
            Forecast forecast = new Forecast(COUNTRY, city);
            forecast.setDay3Max(get3Day(MAX));
            forecast.setDay3Min(get3Day(MIN));
            forecast.setDay7Max(get7day(MAX));
            forecast.setDay7Min(get7day(MIN));
            return forecast;
        } catch (Exception e){
            LoggerUtils.logFailedScrap(logger,city);
            System.out.println(e);
            return null;
        }
    }

    private Float get7day(String minOrMax) {
        Document cityPage610 = SoupUtils.getPage(cityPage.location() + "/6_10/");
        String temperature = cityPage610
                .getElementsByClass("dayoffMonth")
                .stream()
                .filter(e -> e.getElementsContainingOwnText(DateUtils.getDateAddForPl(6)).size() > 0)
                .findFirst()
                .get()
                .parent()
                .getElementsByClass(minOrMax)
                .text()
                .replace("°C","");
        LoggerUtils.logTemperature(logger, COUNTRY,city,7,Float.valueOf(temperature));
        return  Float.valueOf(temperature);
    }

    private Float get3Day(String minOrMax) {
        String temperature = cityPage
                .getElementsByClass("someDayOffWeek")
                .stream()
                .filter(e -> e.getElementsContainingOwnText(DateUtils.getDateAddForPl(2)).size() > 0)
                .findFirst()
                .get()
                .getElementsByClass(minOrMax)
                .text()
                .replace("°C","");
        LoggerUtils.logTemperature(logger, COUNTRY,city,3,Float.valueOf(temperature));
        return  Float.valueOf(temperature);
    }

    private void setCityPage() {
        String linkTocity = SoupUtils.getPage(LINK + "/en")
                .getElementsByAttributeValueContaining("title",city)
                .stream()
                .filter(e->e.hasAttr("href"))
                .findFirst()
                .get()
                .attr("href");
        cityPage = SoupUtils.getPage(LINK + linkTocity);
    }
}
