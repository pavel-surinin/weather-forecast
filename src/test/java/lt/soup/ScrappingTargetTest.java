package lt.soup;

import lt.soup.web.resources.Lithuania;
import org.junit.Test;

import java.lang.reflect.Constructor;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by Pavel on 2017-04-25.
 */
public class ScrappingTargetTest {

    @Test
    public void shouldCreateTarget(){
        Lithuania lit = null;
        ScrappingTarget target = new ScrappingTarget(lit, "Vilnius", "Kaunas", "Klaipeda");
    }a
}