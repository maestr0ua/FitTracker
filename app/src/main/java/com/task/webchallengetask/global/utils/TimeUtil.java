package com.task.webchallengetask.global.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;



public final class TimeUtil {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy", Locale.US);
    private static final SimpleDateFormat dateFormatDDMM = new SimpleDateFormat("dd/MM", Locale.US);
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.UK);

    public static long getCurrentDay() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    public static String timeToString(long time) {
        return timeFormat.format(new Date(time));
    }

    public static String timeToStringDDMM(long time) {
        return dateFormatDDMM.format(new Date(time));
    }

    public static String dateToString(Date date) {
        if (date != null) {
            return dateFormat.format(date);
        }
        return "";
    }

    public static Date stringToDate(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Calendar getCalendarFromString(String time) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(timeFormat.parse(time.trim()));
            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getStringFromGregorianTime(Date date) {
        if (date != null) {
            return timeFormat.format(date);
        } else {
            return "";
        }
    }

    public static String getStringFromCalendar(Calendar calendar) {
        if (calendar != null) {
            return timeFormat.format(calendar.getTime());
        } else {
            return "";
        }

    }

    public static Date addDayToDate(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return new Date(c.getTime().getTime());
    }

    public static Date addEndOfDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return new Date(c.getTime().getTime());
    }

    public static Date minusDayFromDate(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -days);
        return new Date(c.getTime().getTime());
    }

    public static Calendar addSecondToCalendar(Calendar calendar) {
        calendar.add(Calendar.SECOND, 1);
        return calendar;
    }

    public static int compareDay(long _firstTime, long secondTime) {
        Calendar firstCal = Calendar.getInstance();
        Calendar secondCal = Calendar.getInstance();
        firstCal.setTimeInMillis(_firstTime);
        secondCal.setTimeInMillis(secondTime);
        clearTime(firstCal);
        clearTime(secondCal);
        return firstCal.compareTo(secondCal);
    }

    public static void clearTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    public static int getTimeInSeconds(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        int hours = cal.get(Calendar.HOUR_OF_DAY);
        int minutes = cal.get(Calendar.MINUTE);
        int seconds = cal.get(Calendar.SECOND);
        return hours * 60 * 60 + minutes * 60 + seconds;
    }

}
