package lt.soup.web.resources;

import lt.soup.Actual;
import lt.soup.Forecast;

/**
 * Created by Pavel on 2017-04-19.
 */
public interface WebResource {

    String getCountry();
    Forecast getForecast(String city);
    Actual getActual(String city);

    

}
