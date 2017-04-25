package lt.soup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Pavel on 2017-04-23.
 * Class with static methods to parse dates
 */
public class DateUtils {
    private static final List<String> MONTHS_IN_SWEDISH = Arrays.asList("jan", "feb", "mar", "apr", "maj", "jun", "jul", "aug", "sep", "okt", "nov", "dec");
    private static final List<String> MONTHS_IN_ENGLISH = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");


    /**
     * Parses date to other format adding days passed to params
     *
     * @param numberOfDays  number of days to add
     * @return              date in yyyyMMdd format as String
     */
    public static String getDateAddForLt(int numberOfDays){
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, numberOfDays);
        dt = c.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(dt);
    }
    /**
     * Parses date to other format adding days passed to params
     *
     * @param numberOfDays  number of days to add
     * @return              date in  dd MMMM(01 january) format as String
     */
    public static String getDateAddForPl(int numberOfDays){
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, numberOfDays);
        dt = c.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM", Locale.US);
        return dateFormat.format(dt);
    }

    /**
     * Parses date to other format adding days passed to params
     *
     * @param numberOfDays  number of days to add
     * @return              date in  dd MMM(1 jan), month as 3 letters of sweden months format as String
     */
    public static String getDateForSweden(int numberOfDays) {
        String engDate = getDateAddForPl(numberOfDays);
        String monthEN = engDate.split(" ")[1];
        int index = MONTHS_IN_ENGLISH.indexOf(monthEN);
        String monthSW = MONTHS_IN_SWEDISH.get(index);
        String day = engDate.split(" ")[0];
        return Integer.valueOf(day) + " " + monthSW;
    }

}
