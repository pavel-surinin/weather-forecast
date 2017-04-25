package lt.soup;

import org.hamcrest.CoreMatchers;
import org.jsoup.nodes.Document;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by Pavel on 2017-04-23.
 */
public class SoupUtilsTest {
    @Test
    public void shouldReturnCorrectLink() throws Exception {
        String link = SoupUtils.parseLink("http://google.com//en//ru//li/a.html");
        assertThat(link, is("http://google.com/en/ru/li/a.html"));
    }

    @Test
    public void shouldReturnNotNullValue() throws Exception {
        Document page = SoupUtils.getPage("http://www.google.lt/");
        assertThat(page, notNullValue());
    }

    @Test
    public void shouldReturnNullValue() throws Exception {
        Document testNull = SoupUtils.getPage("http://test.cam");
        assertThat(testNull, nullValue());
    }

}