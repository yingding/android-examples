package com.example.background.workers;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.background.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SaveImageToFileWorker extends Worker {

    private static final String TAG = SaveImageToFileWorker.class.getSimpleName();
    private static final String TITLE = "Blurred Image";
    private static final SimpleDateFormat DATE_FORMATTER =
            new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss z", Locale.getDefault());

    public SaveImageToFileWorker(
            @NonNull Context appContext,
            @NonNull WorkerParameters workerParameters
            ) {
        super(appContext, workerParameters);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context applicationContext = getApplicationContext();

        // slow down the work for cancel example
        WorkerUtils.makeStatusNotification(String.format("Doing %s", TAG), applicationContext);
        WorkerUtils.sleep();

        ContentResolver resolver = applicationContext.getContentResolver();
        try {
            String resourceUri = getInputData().getString(Constants.KEY_IMAGE_URI);

            Bitmap bitmap = BitmapFactory.decodeStream(
                    resolver.openInputStream(Uri.parse(resourceUri))
            );
            String outputUri = MediaStore.Images.Media.insertImage(
                    resolver, bitmap, TITLE, DATE_FORMATTER.format(new Date()));
            if (TextUtils.isEmpty(outputUri)) {
                Log.e(TAG, "Writing to MediaStore failed");
                return Result.failure();
            }
            Data outputData = new Data.Builder().putString(Constants.KEY_IMAGE_URI, outputUri.toString())
                    .build();

            return Result.success(outputData);

        } catch (Exception e) {
            Log.e(TAG, "Unable to save image to Gallery", e);
            return Result.failure();
        }
    }
}
