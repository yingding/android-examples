package com.example.looperserviceexample;

import android.app.Service;
import android.content.Intent;
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
            Log.v(TAG, "arg1 is: " + String.valueOf(msg.arg1));
            stopSelf(msg.arg1);
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

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
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
