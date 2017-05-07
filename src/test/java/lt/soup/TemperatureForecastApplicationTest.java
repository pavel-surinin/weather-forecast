package lt.soup;

import lt.soup.analytics.AnalyticsReport;
import lt.soup.db.Database;
import lt.soup.db.InMemoryDB;
import lt.soup.weather.data.WeatherData;
import lt.soup.web.resources.England;
import lt.soup.web.resources.France;
import lt.soup.web.resources.Lithuania;
import lt.soup.web.resources.Poland;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by Pavel on 2017-04-19.
 */
public class TemperatureForecastApplicationTest {

    @Test
    public void shouldScrapReturnForecastList() {
        ArrayList<Forecast> fcs = new TemperatureForecastApplication()
                .start()
                .scrap(new England(), "London", "Glasgow")
                .getForecasts();

        assertThat(fcs.size(), is(2));
        assertThat(fcs, notNullValue());
    }

    @Test
    public void shouldScrapReturnWeatherDataList() {
        ArrayList<WeatherData> wdl = new TemperatureForecastApplication()
                .start()
                .scrap(new France(), "Paris", "Lille")
                .scrap(new Poland(), "Poznan", "Warsaw")
                .getWeatherDataList();

        assertThat(wdl.size(), is(16));
        assertThat(wdl, notNullValue());
    }

    @Test
    public void shouldScrapSaveToDatabase() throws InterruptedException {
        Database db = new InMemoryDB();
        ArrayList<WeatherData> wdl = new TemperatureForecastApplication()
                .start()
                .setDatabase(db)
                .scrap(new Lithuania(), "Vilnius", "Klaipeda", "Kaunas", "test798")
                .saveToDatabase()
                .getWeatherDataList();

        assertThat(db.findAll().size(), is(18));
    }

    @Test
    public void shouldScrapSaveToDBAnalyze() {
        Database db = new InMemoryDB();
        List<AnalyticsReport> wdl = new TemperatureForecastApplication()
                .start()
                .setDatabase(db)
                .scrap(new Lithuania(), "Vilnius", "Klaipeda", "Kaunas")
                .saveToDatabase()
                .analize();
        assertThat(wdl.size(), is(0));
    }
}