package com.example.wearsensorexamples;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventCallback;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.List;

public class MainActivity extends WearableActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    private static final String TAG = "WearHeartRateTest";

    private TextView mTextView;
    private SensorManager mSensorManager;
    private Sensor mHeartRateSensor;
    private static final boolean SENSOR_WAKE_UP_STATE = false;
    private HeartRateSensorEventListener mSensorListener;
    private static final String BODY_PERMISSION = Manifest.permission.BODY_SENSORS;
    private static final int ID_PERMISSION_REQUEST_READ_BODY_SENSORS = 1;
    private AlertDialog permissionRationalDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text);

        setUpSensorResources();
        setUpPermissionRationalDialog();

        // Enables Always-on
        // setAmbientEnabled();
    }

    private void setUpPermissionRationalDialog() {
        permissionRationalDialog = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("Sensor permission necessary")
                .setMessage("Please grant body sensor permission to measure heart rate ...")
                .setPositiveButton("ALLOW", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requestPermission();
                    }
                })
                .setNegativeButton("NOT NOW", null)
                .create();
    }

    private void setUpSensorResources() {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> availableSensors = mSensorManager.getSensorList(Sensor.TYPE_HEART_RATE);
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
             *
             * Source: https://stackoverflow.com/questions/17337504/need-to-read-android-sensors-with-fixed-sampling-rate
             *
             * us = mu seconds = microseconds
             */
            // TODO use registerListener(Listener, Sensor, int SamplingPeriodUs, int maxReportLatencyUs) to avoid the Application Process interupts to same energy
            mSensorManager.registerListener(mSensorListener, mHeartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    private void unregisterSensor() {
        // mSensorListener is not null only when Sensor is not null
        if (mSensorManager != null && mSensorListener != null) {
            mSensorManager.unregisterListener(mSensorListener);
        }
    }

    private void updateTextView(final float heartRate) {
        if (mTextView != null) {
            Log.v(TAG, String.format("UpdateTextView is called with heart rate: %f", heartRate));

            // Warning: the formatting string in android string must follow the java.String.format syntax
            mTextView.setText(MainActivity.this.getString(R.string.text_heart_rate_value, heartRate));
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
        // registerSensor();
        Log.v(TAG, "onResume called");
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
            if (event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
                if (event.accuracy != SensorManager.SENSOR_STATUS_UNRELIABLE && event.accuracy != SensorManager.SENSOR_STATUS_NO_CONTACT) {
                    if (event.values != null && event.values.length > 0) {
                        Log.v("Wear", String.format("%f", event.values[0]));
                        // show the heart rate value
                        MainActivity.this.updateTextView(event.values[0]);
                    }
                }
            }
        }
    }
}
