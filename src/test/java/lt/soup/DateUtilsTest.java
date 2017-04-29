package lt.soup;

import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;
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

    private static final String DATE19990903 = "1999 09 03";

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
            assertThat(date.startsWith("0"), is(false));
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

    @Test
    public void getDateFromString(){
        Date date = DateUtils.getDateFromString(DATE19990903);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        assertThat(year, is(1999));
        assertThat(month, is(8));
        assertThat(day, is(3));
    }

    @Test
    public void getDateAsString(){
        String dateAsString = DateUtils.getDateAsString(DateUtils.getDateFromString(DATE19990903));
        String[] split = dateAsString.split(" ");

        assertThat(split[0], is("1999"));
        assertThat(split[1], is("09"));
        assertThat(split[2], is("03"));
    }
}