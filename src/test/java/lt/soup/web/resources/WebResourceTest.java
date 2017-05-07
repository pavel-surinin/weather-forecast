package lt.soup.web.resources;

import lt.soup.Actual;
import lt.soup.DateUtils;
import lt.soup.Forecast;
import lt.soup.weather.data.Level;
import lt.soup.weather.data.WeatherData;
import lt.soup.weather.data.WeatherGetType;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by Pavel on 2017-04-19.
 */
public abstract class WebResourceTest {

    private WebResource webResource;
    private String name;
    private String city;

    public void setCity(String city) {
        this.city = city;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWebResource(WebResource webResource) {
        this.webResource = webResource;
    }

    @Test
    public void shouldResourceReturnValidCountry() throws Exception {
        assertThat(webResource.getCountry(), is(name));
    }

    @Test
    public void shouldForecastGotValidValues() throws Exception {
        Forecast forecast = webResource.getForecast(city);
        assertTrue(forecast.getDay3Max() < 60);
        assertTrue(forecast.getDay3Min() > -60);
        assertTrue(forecast.getDay7Max() < 60);
        assertTrue(forecast.getDay7Min() > -60);
        assertTrue(forecast.getDay3Min() < forecast.getDay3Max());
        assertTrue(forecast.getDay7Min() < forecast.getDay7Max());
    }

    @Test
    public void shouldReturnNullForNonCity(){
        Forecast forecast = webResource.getForecast("test54545468431");
        assertThat(forecast, nullValue());
        Actual actual = webResource.getActual("saderdfsdf");
        assertThat(actual, nullValue());
    }

    @Test
    public void shouldReturnDateObj(){
        Forecast forecast = webResource.getForecast(city);
        Date date = forecast.getDateScrapped();
        assertThat(date, instanceOf(Date.class));
    }

    @Test
    public void shouldReturnActualWeather(){
        List<WeatherData> actual = webResource.getActual(city).getWeatherDatas();
        boolean isMin = actual.stream().anyMatch(wd -> wd.getLevel().equals(Level.MIN));
        boolean isMax = actual.stream().anyMatch(wd -> wd.getLevel().equals(Level.MAX));

        assertThat(actual.size(), is(2));
        assertTrue(isMax);
        assertTrue(isMin);

        if (actual.get(0).getLevel().equals(Level.MIN)){
            assertThat(actual.get(0).getTemperature() < actual.get(1).getTemperature(), is(true));
        } else {
            assertThat(actual.get(1).getTemperature() < actual.get(0).getTemperature(), is(true));
        }
        actual.forEach(wd->{
            assertThat(wd, instanceOf(WeatherData.class));
            assertThat(wd, notNullValue());
            assertThat(DateUtils.getDateAsString(wd.getDate()), is(DateUtils.getDateAsString(new Date())));
            assertTrue(wd.getTemperature() > -60);
            assertTrue(wd.getTemperature() < 60);
            assertThat(wd.getWeatherGetType(), is(WeatherGetType.ACTUAL));
            assertThat(wd.getCountry(), is(webResource.getCountry()));
            assertThat(wd.getCity(), is(city));
        });
    }
}