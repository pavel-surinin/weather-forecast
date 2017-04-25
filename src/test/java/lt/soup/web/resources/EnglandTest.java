package lt.soup.web.resources;

import static org.junit.Assert.*;

/**
 * Created by Pavel on 2017-04-22.
 */
public class EnglandTest extends WebResourceTest {
    public EnglandTest() {
        super.setName("England");
        super.setWebResource(new England());
        super.setCity("London");
    }

}