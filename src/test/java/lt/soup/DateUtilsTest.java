package lt.soup;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by Pavel on 2017-04-23.
 */
public class DateUtilsTest {

    @Test
    public void getDateAddForLithuania() throws Exception {
        String date = DateUtils.getDateAddForLt(100);
        assertThat(date.length(), is(8));
        assertThat(date, notNullValue());
    }

    @Test
    public void getDateAddForPoland() {
        List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        for (int i = 0; i < 366; i++) {
            String date = DateUtils.getDateAddForPl(i);
            Integer day = Integer.valueOf(date.split(" ")[0]);
            assertTrue(day > 0 && day < 32);
            assertTrue(months.contains(date.split(" ")[1]));
        }
    }

    @Test
    public void getDateForSweden() {
        List<String> months = Arrays.asList("jan", "feb", "mar", "apr", "maj", "jun", "jul", "aug", "sep", "okt", "nov", "dec");
        for (int i = 0; i < 366; i++) {
            String date = DateUtils.getDateForSweden(i);
            Integer day = Integer.valueOf(date.split(" ")[0]);
            assertTrue(day > 0 && day < 32);
            assertTrue(months.contains(date.split(" ")[1]));
            assertThat(date.startsWith("0"), is(false));
        }
    }
}