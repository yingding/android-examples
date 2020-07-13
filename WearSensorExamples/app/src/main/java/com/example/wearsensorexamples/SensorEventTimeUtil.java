package com.example.wearsensorexamples;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.SystemClock;
import android.util.Log;

import java.util.Date;

/**
 * based on the Stack Overflow Referece:
 * https://stackoverflow.com/questions/5500765/accelerometer-sensorevent-timestamp
 *
 * This class {@link SensorEventTimeUtil} is implemented to convert the sensor event timestamp to a utc timestamp of sensor measurement.
 */
public final class SensorEventTimeUtil {

    // https://stackoverflow.com/questions/1459656/how-to-get-the-current-time-in-yyyy-mm-dd-hhmisec-millisecond-format-in-java
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS zz");
    private static final String UTC_TZ_ID_STR = "UTC";

    /**
     * Nano time ns = 10e-9 , micro us = 1000 * ns = 10e-6, milli = 10e-3, e for exponential
     * @param sensorNanoTime
     * @return
     */
    public static long getMilli1(long sensorNanoTime) {
        return new Date().getTime() + (sensorNanoTime - System.nanoTime()) / 1000000L;
    }

    public static long getMilli2(long sensorNanoTime) {
        return System.currentTimeMillis() + (sensorNanoTime - System.nanoTime()) / 1000000L;
    }

    /**
     * convert the sensor timestamp which is the sensor time elapsed in nano seconds to the system up time.
     * @param sensorNanoTime
     * @return utc timestamp with milli seconds
     */
    public static long getTimestampUtcBySensorEventTime(long sensorNanoTime) {
        return System.currentTimeMillis() + (sensorNanoTime - SystemClock.elapsedRealtimeNanos()) / 1000000L;
    }

    private long getMilli0() {
        return System.currentTimeMillis();
    }

    /**
     * optimized time comparison output to get the event.timestamp to utc timestamp of event
     * @param sensorNanoTime
     * @return Long[] which can be used to the varargs for String.format("", Long[])
     */
    public static Long[] convert2UtcTimestamps(long sensorNanoTime) {
        long sysDateTime = new Date().getTime();
        long sysCurTimeMillis = System.currentTimeMillis();
        long sysNanoTime = System.nanoTime();
        long sysElapsedRTNanos = SystemClock.elapsedRealtimeNanos();
        long million = 1000000L;

        long sensorSysNanoDiff = (sensorNanoTime - sysNanoTime) / million;
        long sensorSysElapseRTDiff = (sensorNanoTime - sysElapsedRTNanos) / million;

        Long[] utcs = new Long[4];
        utcs[0] = sysCurTimeMillis;
        utcs[1] = sysDateTime + sensorSysNanoDiff;
        utcs[2] = sysCurTimeMillis + sensorSysNanoDiff;
        utcs[3] = sysCurTimeMillis + sensorSysElapseRTDiff;

        return utcs;
    }

    public static String[] convertUtcTimestamp2LocalTimeStr(Long... utcTSMillis) {
        Calendar cal = Calendar.getInstance(); // calendar instance get the default system tz
        TimeZone localTZ = cal.getTimeZone(); // read default sys tz
        /* debug: is it local time? */
        // Log.d("Local Time Zone: ", localTZ.getDisplayName());
        String[] utcStrArray = {};
        if (utcTSMillis != null && utcTSMillis.length > 0) {
            int n = utcTSMillis.length;
            // init result with input length
            utcStrArray = new String[n];
            for (int i=0; i<n; i++) {
                utcStrArray[i] = convertUtcTimestamp2LocalTimeStr(utcTSMillis[i], localTZ, cal);
            }
        }

        return utcStrArray;
    }

    /**
     * Reference: https://stackoverflow.com/questions/13473073/convert-a-date-and-time-string-according-to-utc-format
     *
     * @param utcTSMilli
     * @param localTZ
     * @param cal
     * @return
     */
    public static String convertUtcTimestamp2LocalTimeStr(long utcTSMilli, TimeZone localTZ, Calendar cal) {
        // Calendar cal = Calendar.getInstance(); // calendar instance get the default system tz
        // TimeZone localTZ = cal.getTimeZone(); // read default sys tz
        /* debug: is it local time? */
        // Log.d("Local Time Zone: ", localTZ.getDisplayName());

        // reset utc tz
        cal.setTimeZone(TimeZone.getTimeZone(UTC_TZ_ID_STR)); // reset the calendar instance tz to utc
        // set time with utc time milli
        cal.setTimeInMillis(utcTSMilli);
        Date date = cal.getTime(); // get date with utc tz

        /* date formatter in local timezone */
        sdf.setTimeZone(localTZ); // set the simple format to sys default tz

        return sdf.format(date); // convert the utc date to sys tz
    }
}
