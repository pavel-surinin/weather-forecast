package lt.soup.web.resources;

import lt.soup.Forecast;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by Pavel on 2017-04-19.
 */
public abstract class WebResourceTest {

    WebResource webResource;
    String name;
    String city;

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
    }

    @Test
    public void shouldReturnNullForNonCity(){
        Forecast forecast = webResource.getForecast("test54545468431");
        assertThat(forecast, nullValue());
    }

    @Test
    public void shouldReturnDateObj(){
        Forecast forecast = webResource.getForecast(city);
        Date date = forecast.getDateScrapped();
        assertThat(date, instanceOf(Date.class));
        assertThat(date.compareTo(new Date()), is(-1));
    }
}