package com.example.chatroom.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeUtils {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
    private static final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static final SimpleDateFormat sdfUTC = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());

    public static String getUnixTime() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static String now() {
        return sdf.format(new Date());
    }

    public static String now2() {
        return sdf2.format(new Date());
    }

    public static String nowUTC() {
        return sdfUTC.format(new Date());
    }

    public static int getCurrentYear() {
        return Integer.parseInt(now().substring(0, 4));
    }

    public static int getCurrentMonth() {
        return Integer.parseInt(now().substring(5, 7));
    }

    public static int getCurrentDay() {
        return Integer.parseInt(now().substring(8, 10));
    }

    public static int getCurrentHour() {
        return Integer.parseInt(now().substring(11, 13));
    }

    public static int getCurrentMinute() {
        return Integer.parseInt(now().substring(14, 16));
    }

    public static String getFirstDayPreviousSecond(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1, 0, 0, -1);
        return sdfUTC.format(calendar.getTime());
    }

    public static String getNextMonthFirstDay(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1, 0, 0, 0);
        return sdfUTC.format(calendar.getTime());
    }

    public static boolean isAfter(String nowTime, String expireTime) {
        try {
            Date nowDate = sdf.parse(nowTime);
            Date expireDate = sdf.parse(expireTime);
            return nowDate.after(expireDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getTodayAddDay(int addDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, addDay);
        return sdf3.format(calendar.getTime());
    }

    public static String confirmReceivePaymentDateToUTCTime(String paymentDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            Date date = sdf.parse(paymentDate);
            SimpleDateFormat sdfUTC = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            sdfUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
            return sdfUTC.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toUTCTime(String text){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = sdf.parse(text);
            SimpleDateFormat sdfUTC = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            sdfUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
            return sdfUTC.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String utcToLocalTime(String text) {
        try {
            SimpleDateFormat sdfFrom = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
            sdfFrom.setTimeZone(TimeZone.getTimeZone("UTC"));
            SimpleDateFormat sdfTo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            return sdfTo.format(sdfFrom.parse(text));
        } catch (Exception e) {
            return text;
        }
    }
}
