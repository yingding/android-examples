package com.example.background.workers;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.background.Constants;
import com.example.background.R;

import java.io.FileNotFoundException;

public class BlurWorker extends Worker {
    private final static String TAG = BlurWorker.class.getSimpleName();

    public BlurWorker(
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

        // parse the input from Data
        String resourceUri = getInputData().getString(Constants.KEY_IMAGE_URI);

        try {
            // Bitmap picture = BitmapFactory.decodeResource(applicationContext.getResources(), R.drawable.test);
            if (TextUtils.isEmpty(resourceUri)) {
                Log.e(TAG, "Invalid input uri");
                throw new IllegalArgumentException("Invalid input uri");
            }
            ContentResolver resolver = applicationContext.getContentResolver();
            // create a bitmap with ContentResolver and Uri
            Bitmap picture = BitmapFactory.decodeStream(
                    resolver.openInputStream(Uri.parse(resourceUri))
            );

            // Blur the bitmap
            Bitmap blurredPicture = WorkerUtils.blurBitmap(picture, applicationContext);

            // Write bitmap to a temp file
            Uri uriBlurredPicture = WorkerUtils.writeBitmapToFile(applicationContext, blurredPicture);

            WorkerUtils.makeStatusNotification("Output is "
                    + uriBlurredPicture.toString(), applicationContext);

            Data outputData = new Data.Builder()
                    .putString(Constants.KEY_IMAGE_URI, uriBlurredPicture.toString())
                    .build();
            // If there were no errors, return SUCCESS
            return Result.success(outputData);
        } catch (Throwable e) {
            // e.printStackTrace();

            // Technically WorkManager will return Result.failure()
            // but it's best to be explicit about it.
            // Thus if there were errors, we're return FAILURE
            Log.e(TAG, "Error applying blur", e);
            return Result.failure();
        }
    }

}