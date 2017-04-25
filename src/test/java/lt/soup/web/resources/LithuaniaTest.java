package lt.soup.web.resources;

import static org.junit.Assert.*;

/**
 * Created by Pavel on 2017-04-22.
 */
public class LithuaniaTest extends WebResourceTest{

    public LithuaniaTest() {
        super.setName("Lithuania");
        super.setWebResource(new Lithuania());
        super.setCity("Vilnius");
    }

}