package com.example.looperserviceexample;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

/**
 * Due to the background execution limit above API level 26
 * https://developer.android.com/about/versions/oreo/background.html
 *
 * please consider to use a scheduled job instead
 * https://developer.android.com/guide/background
 *
 */
public class SyncTCPService extends Service {
    private static final String KEY_COUNTS = "C";
    private static final String KEY_CURRENT_JOB = "J";

    private static final String TAG = SyncTCPService.class.getSimpleName();

    // Looper Object waits for messages in a while loop.
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        /**
         * Do some work here, like synchronize or download data
         * For our sample, we just sleep for 5 seconds.
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {

            Bundle repeats = msg.getData();
            int count = getCount(repeats);
            int jobId = getCurrent(repeats);

            long endTime = System.currentTimeMillis() + 5 * 1000;
            while (System.currentTimeMillis() < endTime) {
                synchronized (this) {
                    try {
                        wait(endTime - System.currentTimeMillis());
                    } catch (Exception e) {

                    }
                }
            }
            // stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
            Log.v(TAG, "arg1 is: " + String.valueOf(msg.arg1) +  " current job id is: " + jobId );

            // increase the job ID
            jobId ++;

            if (jobId < count) {
                Message msg_new = mServiceHandler.obtainMessage();
                msg_new.arg1 = msg.arg1; // does startId indicate how many time the startService is called for starting this service?
                // Service is created in android as singleTon

                repeats.putInt(KEY_CURRENT_JOB, jobId);
                msg_new.setData(repeats);
                mServiceHandler.sendMessage(msg_new);

            } else {
                stopSelf(msg.arg1);
            }
        }

        private int getCount(Bundle data) {
            return data.getInt(KEY_COUNTS);
        }
        private int getCurrent(Bundle data) {
            return data.getInt(KEY_CURRENT_JOB);
        }
    }

    @Override
    public void onCreate() {
        // Start up the thread running the service. Note that we create a
        // separate thread because the service normally runs in the process's
        // mean thread, wiche we don't want to bloack. We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        HandlerThread thread = new HandlerThread("ServiceStartArguements", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        // we can also use this on a separate process, through given a name to the android:process="com.example.looperserviceexample.process2"
        // parameter in the service xml declaration in AndroidManifest.xml file of this project
        // In this case, the HandlerThread will be created in a separated process other to the MainThread of the App Project

        // Get the HandlerThread's Looper and use it for our Handler
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
        Log.v(TAG, "Service Created with Obj Id:" + this.toString());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

        Bundle data = intent.getExtras();
        int count = data.getInt(MainActivity.KEY_INT_REPEATS); // time to repeat

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId; // does startId indicate how many time the startService is called for starting this service?
                            // Service is created in android as singleTon

        Bundle repeats = new Bundle();
        repeats.putInt(KEY_COUNTS, count);
        repeats.putInt(KEY_CURRENT_JOB, 0);
        msg.setData(repeats);

        mServiceHandler.sendMessage(msg);

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }




    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
        Log.v(TAG, "Service destroyed!");
    }
}
