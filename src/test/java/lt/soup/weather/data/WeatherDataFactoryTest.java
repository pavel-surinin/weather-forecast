package lt.soup.weather.data;

import lt.soup.Forecast;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Created by Pavel on 2017-04-26.
 */
public class WeatherDataFactoryTest {

    private static final String LITHUANIA = "Lithuania";
    private static final String VILNIUS = "Vilnius";
    private static final float DAY_3_MAX = 20F;
    private static final float DAY_3_MIN = 10F;
    private static final float DAY_7_MAX = 22F;
    private static final float DAY_7_MIN = 12F;
    private static final String LONDON = "London";
    private static final String ENGLAND = "England";
    private Forecast forecastLT = new Forecast(LITHUANIA, VILNIUS);
    private Forecast forecastEN = new Forecast(ENGLAND, LONDON);

    private WeatherDataFactory weatherDataFactory = new WeatherDataFactory(forecastLT);

    @Before
    public void setUp() {
        forecastLT.setDay3Max(DAY_3_MAX);
        forecastLT.setDay3Min(DAY_3_MIN);
        forecastLT.setDay7Max(DAY_7_MAX);
        forecastLT.setDay7Min(DAY_7_MIN);
        forecastEN.setDay3Max(DAY_3_MAX);
        forecastEN.setDay3Min(DAY_3_MIN);
        forecastEN.setDay7Max(DAY_7_MAX);
        forecastEN.setDay7Min(DAY_7_MIN);
    }

    @Test
    public void shouldReturnCorrectListSize() {
        ArrayList<WeatherData> dataList = weatherDataFactory.getDataList();
        assertThat(dataList.size(), is(4));
        weatherDataFactory = new WeatherDataFactory(forecastEN,forecastLT);
        ArrayList<WeatherData> dataList8 = weatherDataFactory.getDataList();
        assertThat(dataList8.size(), is(8));

    }

    @Test
    public void shouldReturnDataMin3d() {
        ArrayList<WeatherData> dataList = weatherDataFactory.getDataList();
        WeatherData weatherData = getWeatherData(dataList,WeatherMinMax.MIN, 2);
        assertThat(weatherData, notNullValue());
        assertThat(weatherData.getCountry(), is(LITHUANIA));
        assertThat(weatherData.getCity(), is(VILNIUS));
        assertThat(weatherData.getWeatherGetType(), is(WeatherGetType.FORECAST));
        assertThat(weatherData.getTemperature(), is(DAY_3_MIN));
    }

    @Test
    public void shouldReturnDataMin7d() {
        ArrayList<WeatherData> dataList = weatherDataFactory.getDataList();
        WeatherData weatherData = getWeatherData(dataList,WeatherMinMax.MIN, 6);
        assertThat(weatherData, notNullValue());
        assertThat(weatherData.getCountry(), is(LITHUANIA));
        assertThat(weatherData.getCity(), is(VILNIUS));
        assertThat(weatherData.getWeatherGetType(), is(WeatherGetType.FORECAST));
        assertThat(weatherData.getTemperature(), is(DAY_7_MIN));
    }

    @Test
    public void shouldReturnDataMax3d() {
        ArrayList<WeatherData> dataList = weatherDataFactory.getDataList();
        WeatherData weatherData = getWeatherData(dataList,WeatherMinMax.MAX, 2);
        assertThat(weatherData, notNullValue());
        assertThat(weatherData.getCountry(), is(LITHUANIA));
        assertThat(weatherData.getCity(), is(VILNIUS));
        assertThat(weatherData.getWeatherGetType(), is(WeatherGetType.FORECAST));
        assertThat(weatherData.getTemperature(), is(DAY_3_MAX));
    }

    @Test
    public void shouldReturnDataMax7d() {
        ArrayList<WeatherData> dataList = weatherDataFactory.getDataList();
        WeatherData weatherData = getWeatherData(dataList,WeatherMinMax.MAX, 6);
        assertThat(weatherData, notNullValue());
        assertThat(weatherData.getCountry(), is(LITHUANIA));
        assertThat(weatherData.getCity(), is(VILNIUS));
        assertThat(weatherData.getWeatherGetType(), is(WeatherGetType.FORECAST));
        assertThat(weatherData.getTemperature(), is(DAY_7_MAX));
    }

    private WeatherData getWeatherData(ArrayList<WeatherData> dataList, WeatherMinMax mm, int i) {
        return dataList.stream()
                .filter(d -> d.getWeatherMinMax() == mm)
                .filter(d -> getDate(d.getDate()).equals(getDatePlus(i)))
                .findFirst()
                .get();
    }

    private String getDatePlus(int days) {
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, days);
        dt = c.getTime();
        DateFormat dateFormat = new SimpleDateFormat("MM dd");
        return dateFormat.format(dt);
    }

    private String getDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        date = c.getTime();
        DateFormat dateFormat = new SimpleDateFormat("MM dd");
        return dateFormat.format(date);
    }

}