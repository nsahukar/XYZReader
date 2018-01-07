package com.nsahukar.android.base.common.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Nikhil on 26/12/17.
 */

public class DateUtils {
    private static final String TAG = DateUtils.class.getSimpleName();
    private static final GregorianCalendar START_OF_EPOCH = new GregorianCalendar(2,1,1);
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss");
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat OUTPUT_FORMAT = new SimpleDateFormat("dd MMM yyyy");

    private static Date parseDate(String stringDate) {
        try {
            return DATE_FORMAT.parse(stringDate);
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage());
            Log.i(TAG, "passing today's date");
            return new Date();
        }
    }

    public static String getFormattedDate(String stringDate) {
        Date date = parseDate(stringDate);
        if (!date.before(START_OF_EPOCH.getTime())) {
            return android.text.format.DateUtils.getRelativeTimeSpanString(
                    date.getTime(),
                    System.currentTimeMillis(),
                    android.text.format.DateUtils.HOUR_IN_MILLIS,
                    android.text.format.DateUtils.FORMAT_ABBREV_ALL).toString();
        } else {
            return OUTPUT_FORMAT.format(date);
        }
    }
}
