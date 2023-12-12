package com.jdbcdemo.common.wrapper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeWrapper {
    public static final String dateTimeFormat1 = "yyyy-MM-dd HH:mm:ss.SSS";
    public static Date now() {
        return new Date();
    }

    public static String now(String format) {
        return new SimpleDateFormat(format).format(now());
    }

    public static String getTimestamp() {
        return String.valueOf(System.currentTimeMillis());
    }
}
