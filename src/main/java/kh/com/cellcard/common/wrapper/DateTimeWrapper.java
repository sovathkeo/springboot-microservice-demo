package kh.com.cellcard.common.wrapper;

import kh.com.cellcard.common.enums.datetime.DatetimeUnit;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class DateTimeWrapper {
    public static final String dateTimeFormat1 = "yyyy-MM-dd HH:mm:ss.SSS";
    public static Date now() {
        return new Date();
    }

    public static Date defaultDate() { return Date.from(Instant.EPOCH); }

    public static Date fromString(String dateStr, String format) {
        try {
            return new SimpleDateFormat(format).parse(dateStr);
        } catch (ParseException e) {
            return defaultDate();
        }

    }

    public static String now(String format) {
        return new SimpleDateFormat(format).format(now());
    }

    public static String toString(Date date,String format) { return format(date, format); }

    public static String getTimestampMillis() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static Date addDate(Date date, int value, DatetimeUnit unit) {
        return switch (unit){
            case YEAR -> DateUtils.addYears(date, value);
            case MONTH -> DateUtils.addMonths(date, value);
            case DAY -> DateUtils.addDays(date, value);
            case HOUR -> DateUtils.addHours(date, value);
            case MINUTE -> DateUtils.addMinutes(date, value);
            case SECOND -> DateUtils.addSeconds(date, value);
            case MILLISECOND -> DateUtils.addMilliseconds(date, value);
        };
    }

    private static String format(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static int compare(Date d1, Date d2) {
        return d1.compareTo(d2);
    }

    public static boolean isBeforeOther(Date date, Date other){
        return compare(date, other) < 0;
    }

    public static boolean isAfterOther(Date date, Date other){
        return compare(date, other) > 0;
    }

    public static boolean isEqualToOther(Date date, Date other){
        return compare(date, other) == 0;
    }

    public static boolean isBeforeOrEqualOther(Date date, Date other) {
        return isBeforeOther(date, other) || isEqualToOther(date, other);
    }
}
