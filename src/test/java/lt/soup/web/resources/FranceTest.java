package lt.soup.web.resources;

import static org.junit.Assert.*;

/**
 * Created by Pavel on 2017-04-22.
 */
public class FranceTest extends WebResourceTest {

    public FranceTest() {
        super.setName("France");
        super.setWebResource(new France());
        super.setCity("Paris");
    }

}