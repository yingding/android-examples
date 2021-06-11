package com.example.wearsensorexamples;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventCallback;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.wear.ambient.AmbientModeSupport;

import java.util.List;

public class MainActivity extends FragmentActivity implements AmbientModeSupport.AmbientCallbackProvider, ActivityCompat.OnRequestPermissionsResultCallback {
    private static final String TAG = "WearHeartRateTest";

    private TextView mTextView;
    private TextView sensorInfoTextView;
    private SensorManager mSensorManager;
    private Sensor mHeartRateSensor;
    private static final boolean SENSOR_WAKE_UP_STATE = false; // This must be false for the heart rate
    private HeartRateSensorEventListener mSensorListener;
    private static final String BODY_PERMISSION = Manifest.permission.BODY_SENSORS;
    private static final int ID_PERMISSION_REQUEST_READ_BODY_SENSORS = 1;
    private AlertDialog permissionRationalDialog;
    private static final int SAMPLING_RATE_1HZ_MICRO_SECS = 1000000; // 10e6 to micro seconds 10e-6

    /* the viewport inside is sqrt(2) of the original, the factor of display high can be calculated as (1 - 1/sqrt(2)) / 2
     * this gives the FACTOR is the padding needed for the left side ( /2 is because calculate with radius r, and DisplayMetric return 2r)
     */
    private static final float FACTOR = 0.146467f;
    private ViewGroup layoutContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.text);
        sensorInfoTextView = findViewById(R.id.sensorInfoText);

        setUpSensorResources();
        setUpPermissionRationalDialog();

        // Enables Always-on
        // Reference: https://developer.android.com/training/wearables/apps/always-on
        // codeExamples: https://github.com/android/wear-os-samples/tree/master/AlwaysOn
        // setAmbientEnabled();
        AmbientModeSupport.AmbientController controller = AmbientModeSupport.attach(this);

        layoutContent = findViewById(R.id.layout_content);
        adjustInset();
    }

    private void adjustInset() {
        /* layoutContent is the Linear Layout conprise all the element, adding padding so that it can be seen properly
         * for round watchface
         * https://bitbucket.org/snippets/devdev-dev/keR67A
         */
        if (layoutContent != null && getApplicationContext().getResources().getConfiguration().isScreenRound()) {
            // get the display width in pixel and set that for dp as inset
            int inset = (int) (FACTOR * Resources.getSystem().getDisplayMetrics().widthPixels);
            layoutContent.setPadding(inset, inset, inset, inset);
        }
    }

    private void setUpPermissionRationalDialog() {
        permissionRationalDialog = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle(R.string.text_title_sensor_permission_needed)
                .setMessage(R.string.text_rational_sensor_permission_needed)
                .setPositiveButton(R.string.text_button_allow, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requestPermission();
                    }
                })
                .setNegativeButton(R.string.text_button_not_now, null)
                .create();
    }

    private void setUpSensorResources() {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> availableSensors = null;
        if (mSensorManager != null) { // SensorManager might be null, if called before onCreate()
           availableSensors = mSensorManager.getSensorList(Sensor.TYPE_HEART_RATE);
        }
        if (availableSensors != null && availableSensors.size() > 0) {
            // return a sensor which shall be waked up
            mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE, SENSOR_WAKE_UP_STATE); // Sensor Status UNRELIABLE, NO_CONTACT shall be deleted.
            if (mSensorListener == null) { // create a sensor listener when it is not cached already
                mSensorListener = new HeartRateSensorEventListener();
            }
        } else {
            mHeartRateSensor = null;
            mSensorListener = null;
        }

    }

    private void registerSensor() {
        if (mHeartRateSensor != null && mSensorListener != null) {
            /*
             * register Listener with SENSOR_DELAY_NORMAL 200 milliseconds , which is equal to sampling rate 5Hz
             * Source: https://stackoverflow.com/questions/17337504/need-to-read-android-sensors-with-fixed-sampling-rate
             *
             * us = mu seconds = microseconds
             * Delay (sampling rate) Delay normal = 200 millis,
             */
            // TODO use registerListener(Listener, Sensor, int SamplingPeriodUs, int maxReportLatencyUs) to avoid the Application Process interupts to same energy
            boolean isSuccessful = mSensorManager.registerListener(mSensorListener, mHeartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);

            // mSensorManager.registerListener(mSensorListener, mHeartRateSensor, SAMPLING_RATE_1HZ_MICRO_SECS);
            Log.v(TAG, String.format("registerSensor is %ssuccessful", isSuccessful? "": "NOT "));
        }
    }

    /**
     * Reference: https://developer.android.com/guide/topics/sensors/sensors_overview
     */
    private void updateSensorInfoText() {
        if (sensorInfoTextView != null) {
            if (mHeartRateSensor != null) {
                String vendor = mHeartRateSensor.getVendor();
                String name = mHeartRateSensor.getName();
                int version = mHeartRateSensor.getVersion();
                long minDelay = mHeartRateSensor.getMinDelay(); //https://developer.android.com/guide/topics/sensors/sensors_overview (aka sampling rate)
                // if minDelay is zero, it is a NON Streaming sensor. It only report when there is a change in the parameters it is sensing.
                float resolution = mHeartRateSensor.getResolution();
                float maxRange = mHeartRateSensor.getMaximumRange();
                sensorInfoTextView.setText(MainActivity.this.getString(R.string.text_hr_sensor_info, vendor, name, version, minDelay, resolution, maxRange));
            } else {
                sensorInfoTextView.setText(MainActivity.this.getString(R.string.text_no_sensor_info));
            }
        }
    }

    private void unregisterSensor() {
        // mSensorListener is not null only when Sensor is not null
        if (mSensorManager != null && mSensorListener != null) {
            mSensorManager.unregisterListener(mSensorListener);
        }
    }

    private void updateTextView(final float heartRate, long utcTimestampMilli) {
        if (mTextView != null) {
            // Log.v(TAG, String.format("UpdateTextView is called with heart rate: %f", heartRate));

            // Warning: the formatting string in android string must follow the java.String.format syntax
            mTextView.setText(MainActivity.this.getString(R.string.text_heart_rate_value, SensorEventTimeUtil.convertUtcTimestamp2LocalTimeStr(utcTimestampMilli)[0], heartRate));
        }
    }

    /**
     * https://developer.android.com/training/articles/wear-permissions
     *
     * Runtime permission doc:
     * https://guides.codepath.com/android/Understanding-App-Permissions
     */
    private void validPermission() {
        int code = ContextCompat.checkSelfPermission(this, BODY_PERMISSION);
        Log.v(TAG, "Permission is " + (code == PackageManager.PERMISSION_GRANTED ? "GRANTED": "DENIED") );
        if (code == PackageManager.PERMISSION_DENIED) {
            if (shouldShowRequestPermissionRationale(BODY_PERMISSION)) {
                // show a rationale UI
                if (MainActivity.this.permissionRationalDialog != null) {
                    MainActivity.this.permissionRationalDialog.show();
                }
//                ContextCompat.getMainExecutor(MainActivity.this).execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(MainActivity.this, R.string.text_toast_show_rational, Toast.LENGTH_LONG)
//                                .show();
//                    }
//                });
                Log.v(TAG, "need show request rational!");
            } else {
                requestPermission();
            }
        } else {
            // has permission can register sensor
            registerSensor();
        }
    }

    private void requestPermission() {
        // request permission
        ActivityCompat.requestPermissions(this, new String[]{BODY_PERMISSION}, ID_PERMISSION_REQUEST_READ_BODY_SENSORS);
    }

    @Override
    protected void onResume() {
        super.onResume();
        validPermission();
        updateSensorInfoText();
        // registerSensor();
        Log.v(TAG, "onResume called");
        Log.v(TAG, String.format("Listener is %snull!", this.mSensorListener == null ? "": "NOT "));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterSensor();
    }

    /*
     * Call back received when a permission request has been completed
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                    @NonNull int[] grantResults) {
        if (requestCode == ID_PERMISSION_REQUEST_READ_BODY_SENSORS) {
            if ((grantResults.length == 1) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // setUpSensorResources();
                registerSensor();
            }
        }
    }

    // API Level >= 24 extension of SensorEventListener and SensorEventListener2
    private class HeartRateSensorEventListener extends SensorEventCallback {
        // Called when there is a new sensor event
        @Override
        public void onSensorChanged(SensorEvent event) {
            super.onSensorChanged(event);
            Log.d(TAG, String.format("onSensorChanged of event type: %d , %s ", event.sensor.getType(), event.sensor.getName() ));
            if (event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
                if (event.accuracy != SensorManager.SENSOR_STATUS_UNRELIABLE && event.accuracy != SensorManager.SENSOR_STATUS_NO_CONTACT) {
                    if (event.values != null && event.values.length > 0) {
                        Log.d(TAG, String.format("onSensorChanged: value: %f", event.values[0]));
                        Log.d(TAG, String.format("onSensorChanged: nano timestamp : %d", event.timestamp));

                        outputEventUTCTimestamps(event.timestamp);

                        // show the heart rate value
                        MainActivity.this.updateTextView(event.values[0], SensorEventTimeUtil.getTimestampUtcBySensorEventTime(event.timestamp));
                    }
                }
            }
        }
    }

    /**
     * this method examines which method is the correct one to convert the sensor event timestamp to a utc timestamp in milliseconds unit.
     * @param sensorEventNano {@link android.hardware.SensorEvent#timestamp}
     */
    private void outputEventUTCTimestamps(long sensorEventNano) {
        Long[] utcTimestamps = SensorEventTimeUtil.convert2UtcTimestamps(sensorEventNano);
        Log.v(TAG, String.format("Event UTC timestamp:\nEventSysCurrentMilli: %d\nDateTime+sensor,SysNanoDiff: %d\n" +
                "SysCurMilli+sensor,SysNanoDiff: %d\nSysCurMilli+sensor,SysElapseRTDiff: %d", utcTimestamps)); // vararg shall not be null
        String[] utcTimeStrs = SensorEventTimeUtil.convertUtcTimestamp2LocalTimeStr(utcTimestamps);
        Log.v(TAG, String.format("Event UTC time string:\nEventSysCurrentMilli: %s\nDateTime+sensor,SysNanoDiff: %s\n" +
                "SysCurMilli+sensor,SysNanoDiff: %s\nSysCurMilli+sensor,SysElapseRTDiff: %s", utcTimeStrs)); // vararg shall not be null
    }

    /*
     * Call back on Ambient change
     */
    @Override
    public AmbientModeSupport.AmbientCallback getAmbientCallback() {
        return new MyAmbientCallback();
    }

    private class MyAmbientCallback extends AmbientModeSupport.AmbientCallback {

        @Override
        public void onEnterAmbient(Bundle ambientDetails) {
            super.onEnterAmbient(ambientDetails);
            Log.v(TAG, "entering Ambient mode...");
        }

        @Override
        public void onUpdateAmbient() {
            super.onUpdateAmbient();
            Log.v(TAG, "onUpdateAmbient!");
        }

        @Override
        public void onExitAmbient() {
            super.onExitAmbient();
            Log.v(TAG, "exiting Ambient mode...");
        }
    }
}