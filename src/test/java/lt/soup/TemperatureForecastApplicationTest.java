package lt.soup;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Pavel on 2017-04-19.
 */
public class TemperatureForecastApplicationTest {

    TemperatureForecastApplication app = new TemperatureForecastApplication();

    @Test
    public void startApplication(){
        app.start();
    }

}