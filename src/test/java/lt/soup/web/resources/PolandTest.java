package lt.soup.web.resources;

/**
 * Created by Pavel on 2017-04-23.
 */
public class PolandTest extends WebResourceTest{

    public PolandTest() {
        super.setName("Poland");
        super.setWebResource(new Poland());
        super.setCity("Poznan");
    }
}