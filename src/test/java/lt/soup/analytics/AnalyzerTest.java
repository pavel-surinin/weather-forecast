package lt.soup.analytics;

import lt.soup.DateUtils;
import lt.soup.weather.data.Level;
import lt.soup.weather.data.WeatherData;
import lt.soup.weather.data.WeatherGetType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Created by Pavel on 2017-04-30.
 */
public class AnalyzerTest {

    private static final String COUNTRY = "Lithuania";
    private static final float AVERAGE_ERROR = 2F;
    private static final String CITY = "Vilnius";
    private static final String OTHER_COUNTRY = "Barahland";
    private static final String DATE_12 = "2010 10 12";
    private static final String DATE_11 = "2010 10 11";
    private static final String DATE_10 = "2010 10 10";
    private List<WeatherData> dataList = new ArrayList<>();

    @Before
    public void setUp(){
        dataList.clear();
        //average err 3
        getWD(Level.MAX,WeatherGetType.FORECAST, DATE_10, 6F, COUNTRY);
        getWD(Level.MAX,WeatherGetType.ACTUAL, DATE_10, 10F,COUNTRY);
        getWD(Level.MAX,WeatherGetType.FORECAST, DATE_11, 6F,COUNTRY);
        getWD(Level.MAX,WeatherGetType.ACTUAL, DATE_11, 3F,COUNTRY);
        getWD(Level.MAX,WeatherGetType.FORECAST, DATE_12, 10F,COUNTRY);
        getWD(Level.MAX,WeatherGetType.ACTUAL, DATE_12, 8F,COUNTRY);
        //average err 1
        getWD(Level.MIN,WeatherGetType.FORECAST, DATE_10, 3F,COUNTRY);
        getWD(Level.MIN,WeatherGetType.ACTUAL, DATE_10, 3F,COUNTRY);
        getWD(Level.MIN,WeatherGetType.FORECAST, DATE_11, 5F,COUNTRY);
        getWD(Level.MIN,WeatherGetType.ACTUAL, DATE_11, 5F,COUNTRY);
        getWD(Level.MIN,WeatherGetType.FORECAST, DATE_12, 10F,COUNTRY);
        getWD(Level.MIN,WeatherGetType.ACTUAL, DATE_12, 7F,COUNTRY);
        //add other country
        getWD(Level.MIN,WeatherGetType.FORECAST, DATE_10, 3F, OTHER_COUNTRY);
        getWD(Level.MIN,WeatherGetType.FORECAST, DATE_11, 3F, OTHER_COUNTRY);
        getWD(Level.MIN,WeatherGetType.FORECAST, DATE_12, 3F, OTHER_COUNTRY);
    }

    @Test
    public void test(){
        List<AnalyticsReport> reports = Analyzer.analyze(dataList);

        assertThat(reports, notNullValue());
        assertThat(reports.size(), is(1));
        assertThat(reports.get(0).getCountry(), is(COUNTRY));
        assertThat(reports.get(0).getAverageError(), is(AVERAGE_ERROR));

    }

    private void getWD(Level mm, WeatherGetType type, String dateStr, Float temp, String country) {
        WeatherData weatherData = new WeatherData();
        weatherData.setCountry(country);
        weatherData.setCity(CITY);
        weatherData.setLevel(mm);
        weatherData.setWeatherGetType(type);
        Date dateFromString = DateUtils.getDateFromString(dateStr);
        weatherData.setDate(dateFromString);
        weatherData.setTemperature(temp);
        dataList.add(weatherData);
    }



}