package com.getmedcheck.lib.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateTimeUtils {
    private DateTimeUtils() {
    }

    public static long getTimeFromStringDate(String format, String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
        try {
            return sdf.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String getFormattedDate(String format, long time) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
        return sdf.format(time);
    }
    public static String format(String format, long time) {
        return new SimpleDateFormat(format, Locale.getDefault()).format(new Date(time));
    }

}
