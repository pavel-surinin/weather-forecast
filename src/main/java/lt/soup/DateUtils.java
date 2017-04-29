package lt.soup;

import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Pavel on 2017-04-23.
 * Class with static methods to parse dates
 */
public class DateUtils {
    private static final Logger log = Logger.getLogger(DateUtils.class);
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
     * @return              date in  dd MMMM(1 january) format as String
     */
    public static String getDateAddForPl(int numberOfDays){
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, numberOfDays);
        dt = c.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM", Locale.US);
        String date = dateFormat.format(dt);
        String day = date.split(" ")[0];
        String month = date.split(" ")[1];
        return Integer.valueOf(day) + " " + month;
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

    /**
     * Add days and return new Date obj
     * @param days  number of days to be added
     * @return      Date obj wi added amount of days
     */
    public static Date getDateAdd(int days) {
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, days);
        dt = c.getTime();
        return dt;
    }

    /**
     * Parses date from string
     *
     * @param dt    Date object date to parse
     * @return      date as String in /'yyyy MM dd/' format
     */
    public static String getDateAsString(Date dt) {
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        dt = c.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy MM dd");
        String date = dateFormat.format(dt);
        return date;
    }

    /**
     * Parses String with date to date Object
     *
     * @param val   date string to parse, must be \'yyyy MM dd\' format
     * @return      Date obj
     */
    public static Date getDateFromString(String val) {
        DateFormat format = new SimpleDateFormat("yyyy MM dd");
        Date date;
        try {
            date = format.parse(val);
        } catch (ParseException e) {
            log.warn("Failed to parse " + val + "to Date");
            log.debug(e);
            return null;
        }
        return date;
    }
}
