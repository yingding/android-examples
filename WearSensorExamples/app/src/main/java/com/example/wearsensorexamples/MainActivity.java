package com.example.wearsensorexamples;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventCallback;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.List;

public class MainActivity extends WearableActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private TextView mTextView;
    private SensorManager mSensorManager;
    private Sensor mHeartRateSensor;
    private static final boolean SENSOR_WAKE_UP_STATE = true;
    private HeartRateSensorEventListener mSensorListener;
    private static final String BODY_PERMISSION = Manifest.permission.BODY_SENSORS;
    private static final int ID_PERMISSION_REQUEST_READ_BODY_SENSORS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text);

        setUpSensorResources();

        // Enables Always-on
        setAmbientEnabled();
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
            // register Listener
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
            mTextView.setText(MainActivity.this.getString(R.string.text_heart_rate_value, heartRate));
        }
    }

    /**
     * https://developer.android.com/training/articles/wear-permissions
     */
    private void validPermission() {
        int code = ContextCompat.checkSelfPermission(this, BODY_PERMISSION);
        if (code == PackageManager.PERMISSION_DENIED) {
            if (shouldShowRequestPermissionRationale(BODY_PERMISSION)) {
                // show a rationale
            } else {
                // request permission
                ActivityCompat.requestPermissions(this, new String[]{BODY_PERMISSION}, ID_PERMISSION_REQUEST_READ_BODY_SENSORS);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        validPermission();
        registerSensor();
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
                        // show the heart rate value
                        MainActivity.this.updateTextView(event.values[0]);
                    }
                }
            }
        }
    }
}
