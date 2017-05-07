package lt.soup;

import lt.soup.db.TextFileDB;
import lt.soup.web.resources.England;
import lt.soup.web.resources.France;
import lt.soup.web.resources.Lithuania;
import lt.soup.web.resources.Poland;

import java.util.concurrent.TimeUnit;

/**
 * Created by Pavel on 2017-04-18.
 */
public class Application {

    private static final String[] ENGLAND_CITIES = {"London", "Edinburgh", "Glasgow", "Bristol", "Manchester", "Leeds", "Exeter", "Cambridge", "Southampton", "Cardiff"};
    private static final String[] FRANCE_CITIES = {"Angers", "Brest", "Grenoble", "Lille", "Marseille", "Nantes", "Paris", "Rennes", "Strassburg", "Bordeaux", "Dijon", "Lyon", "Montpellier", "Nîmes", "Reims", "Saint-Étienne", "Toulouse"};
    private static final String[] LITHUANIA_CITIES = {"Vilnius", "Kaunas", "Klaipeda", "Siauliai", "Panevezys"};
    private static final String[] POLAND_CITIES = {"Bialystok", "Bydgoszcz", "Gdansk", "Katowice", "Kielce", "Krakow", "Lodz", "Lublin", "Olsztyn", "Opole", "Poznan", "Rzeszow", "Szczecin", "Warsaw", "Wroclaw"};

    public static void main(String[] args) {
        sleepForHours(0);
        TemperatureForecastApplication temperatureApp = new TemperatureForecastApplication();
        temperatureApp.start().setDatabase(new TextFileDB());
        while (true) {
            temperatureApp
                    .scrap(new England(), ENGLAND_CITIES)
                    .scrap(new France(), FRANCE_CITIES)
                    .scrap(new Lithuania(), LITHUANIA_CITIES)
                    .scrap(new Poland(), POLAND_CITIES)
                    .saveToDatabase()
                    .analize();
            sleepForHours(24);
        }
    }

    private static void sleepForHours(int h) {
        try {
            TimeUnit.HOURS.sleep(h);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
