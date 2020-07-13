package com.example.wearsensorexamples;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.SystemClock;
import java.util.Date;

/**
 * Based on the <a href="https://stackoverflow.com/questions/5500765/accelerometer-sensorevent-timestamp">Stack Overflow Reference</a>,
 * this class {@link SensorEventTimeUtil} provides methods to convert {@link android.hardware.SensorEvent#timestamp}
 * to an utc timestamp indicating the utc time of the sensor measurement.
 */
public final class SensorEventTimeUtil {

    /**
     * @see <a href="https://stackoverflow.com/questions/1459656/how-to-get-the-current-time-in-yyyy-mm-dd-hhmisec-millisecond-format-in-java">StackOverflow discussion regarding SimpleDateFormat</a>
     */
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS zz");
    private static final String UTC_TZ_ID_STR = "UTC";
    private static final long MILLION = 1000000L; // WARNING: DO NOT change this number

    @Deprecated
    public static long getMilli1(long sensorNanoTime) {
        return new Date().getTime() + (sensorNanoTime - System.nanoTime()) / MILLION;
    }

    @Deprecated
    public static long getMilli2(long sensorNanoTime) {
        return System.currentTimeMillis() + (sensorNanoTime - System.nanoTime()) / MILLION;
    }

    /**
     * this method converts {@link android.hardware.SensorEvent#timestamp},
     * which is the sensor time elapsed in nano seconds to the system uptime to an utc timestamp.
     *
     * @param sensorNanoTime timestamp in ns (nano seconds = 10e-9) originated from {@link android.hardware.SensorEvent#timestamp}
     * @return utc timestamp in ms (millisecond == 10e-3)
     */
    public static long getTimestampUtcBySensorEventTime(long sensorNanoTime) {
        // Nano Seconds (ns, 10e-9), micro seconds (us, 10e-6), milli seconds (ms, 10e-3), where "e" is the abbreviation of exponential
        return System.currentTimeMillis() + (sensorNanoTime - SystemClock.elapsedRealtimeNanos()) / MILLION;
    }

    /**
     * convenient method for {@link System#currentTimeMillis()}
     * @return utc timestamp of now in ms (milliseconds)
     */
    public static long nowInMillis() {
        return System.currentTimeMillis();
    }

    /**
     * this method implements different approachs proposed by
     * <a href="https://stackoverflow.com/questions/5500765/accelerometer-sensorevent-timestamp">StackOverflow Discussion></a>
     * to convert {@link android.hardware.SensorEvent#timestamp} to an utc timestamp.
     *
     * timestamps array::
     * The 1st element is: System.currentTimeMillis()
     * The 2nd element is: Date().getTime() + (sensorNanoTime - System.nanoTime()) / 1000000L
     * The 3rd element is: System.currentTimeMillis() + (sensorNanoTime - System.nanoTime()) / 1000000L;
     * The 4th element is: System.currentTimeMillis() + (sensorNanoTime - SystemClock.elapsedRealtimeNanos()) / 1000000L;
     *
     * @param sensorNanoTime timestamp in nano secs originated from {@link android.hardware.SensorEvent#timestamp}
     * @return Long[]; a Long array of timestamps, which can be used as input Varargs for {@link String#format(String, Object[])}
     */
    public static Long[] convert2UtcTimestamps(long sensorNanoTime) {
        long sysDateTime = new Date().getTime();
        long sysCurTimeMillis = System.currentTimeMillis();
        long sysNanoTime = System.nanoTime();
        long sysElapsedRTNanos = SystemClock.elapsedRealtimeNanos();
        // long million = 1000000L;

        long sensorSysNanoDiff = (sensorNanoTime - sysNanoTime) / MILLION;
        long sensorSysElapseRTDiff = (sensorNanoTime - sysElapsedRTNanos) / MILLION;

        Long[] utcs = new Long[4];
        utcs[0] = sysCurTimeMillis;
        utcs[1] = sysDateTime + sensorSysNanoDiff;
        utcs[2] = sysCurTimeMillis + sensorSysNanoDiff;
        utcs[3] = sysCurTimeMillis + sensorSysElapseRTDiff;

        return utcs;
    }

    /**
     * this method converts the given list of utc timestamps in the milliseconds to utc time string of the system default local timezone time.
     * @param utcTSMillis varargs Long[]
     * @return String[] local timezone time string in the format of {@link SensorEventTimeUtil#sdf}
     */
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
     * this method converts the given utc timestamp in milliseconds unit to
     * a utc time string of the desired timezone like "2020-07-13 22:43:14.793 CEST"
     *
     * Reference: https://stackoverflow.com/questions/13473073/convert-a-date-and-time-string-according-to-utc-format
     *
     * @param utcTSMilli utc timestamp in the unit of milliseconds (ms)
     * @param localTZ desired timezone to show the time in utc time string format
     * @param cal an arbitrary calendar instance like {@link android.icu.util.Calendar#getInstance()} to host temporal value. When this method is called in a loop, this Calendar param can be reused.
     * @return time string of the local time regarding the given timezone in the format of {@link SensorEventTimeUtil#sdf}
     */
    private static String convertUtcTimestamp2LocalTimeStr(long utcTSMilli, TimeZone localTZ, Calendar cal) {
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