package lt.soup.db;

import lt.soup.DateUtils;
import lt.soup.weather.data.WeatherData;
import lt.soup.weather.data.WeatherGetType;
import lt.soup.weather.data.WeatherMinMax;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * Created by Pavel on 2017-04-28.
 */
public abstract class DatabaseTest {

    public static final String VILNIUS = "Vilnius";
    public static final String LITHUANIA = "Lithuania";
    public static final float MIN_TEMP = 10F;
    public static final float MAX_TEMP = 20F;
    private Database database;
    private WeatherData vilniusWeatherDataMin = new WeatherData();
    private WeatherData vilniusWeatherDataMax = new WeatherData();

    @Before
    public void setUp(){
        database.deleteAll();

        vilniusWeatherDataMin.setWeatherGetType(WeatherGetType.FORECAST);
        vilniusWeatherDataMin.setWeatherMinMax(WeatherMinMax.MIN);
        vilniusWeatherDataMin.setTemperature(MIN_TEMP);
        vilniusWeatherDataMin.setDate(new Date());
        vilniusWeatherDataMin.setCity(VILNIUS);
        vilniusWeatherDataMin.setCountry(LITHUANIA);

        vilniusWeatherDataMax.setWeatherGetType(WeatherGetType.FORECAST);
        vilniusWeatherDataMax.setWeatherMinMax(WeatherMinMax.MAX);
        vilniusWeatherDataMax.setTemperature(MAX_TEMP);
        vilniusWeatherDataMax.setDate(new Date());
        vilniusWeatherDataMax.setCity(VILNIUS);
        vilniusWeatherDataMax.setCountry(LITHUANIA);
    }

    @Test
    public void shouldSaveToDatabase(){
        database.save(vilniusWeatherDataMax);
        database.save(vilniusWeatherDataMin);
        assertThat(database.findAll().size(), is(2));
        assertThat(database.findById(1L).getWeatherMinMax(), is(WeatherMinMax.MAX));
        assertThat(database.findById(2L).getWeatherMinMax(), is(WeatherMinMax.MIN));
        assertThat(database.findById(1L).getWeatherMinMax(), is(vilniusWeatherDataMax.getWeatherMinMax()));
        assertThat(database.findById(1L).getTemperature(), is(vilniusWeatherDataMax.getTemperature()));
        assertThat(database.findById(1L).getCity(), is(vilniusWeatherDataMax.getCity()));
        assertThat(database.findById(1L).getCountry(), is(vilniusWeatherDataMax.getCountry()));
        assertThat(database.findById(1L).getWeatherGetType(), is(vilniusWeatherDataMax.getWeatherGetType()));
        assertThat(DateUtils.getDateAsString(database.findById(1L).getDate()), is(DateUtils.getDateAsString(vilniusWeatherDataMax.getDate())));

    }

    @Test
    public void shouldSaveToDatabaseOnlyOne(){
        database.save(vilniusWeatherDataMax);
        database.save(vilniusWeatherDataMax);
        assertFalse(database.save(vilniusWeatherDataMax));
        assertThat(database.findAll().size(), is(1));
        assertThat(database.findById(1L).getWeatherMinMax(), is(vilniusWeatherDataMax.getWeatherMinMax()));
        assertThat(database.findById(1L).getTemperature(), is(vilniusWeatherDataMax.getTemperature()));
        assertThat(database.findById(1L).getCity(), is(vilniusWeatherDataMax.getCity()));
        assertThat(database.findById(1L).getCountry(), is(vilniusWeatherDataMax.getCountry()));
        assertThat(database.findById(1L).getWeatherGetType(), is(vilniusWeatherDataMax.getWeatherGetType()));
        assertThat(DateUtils.getDateAsString(database.findById(1L).getDate()), is(DateUtils.getDateAsString(vilniusWeatherDataMax.getDate())));
    }

    @Test
    public void shouldDeleteAllRecords(){
        database.save(vilniusWeatherDataMax);
        assertThat(database.findAll().size(), is(1));
        database.deleteAll();
        assertThat(database.findAll().size(), is(0));

    }

    public void setDatabase(Database databaseImpl){
        this.database = databaseImpl;
    }

}