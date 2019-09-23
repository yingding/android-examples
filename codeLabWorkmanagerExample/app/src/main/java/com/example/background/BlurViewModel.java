/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.background;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
// import androidx.lifecycle.ViewModel;
import androidx.lifecycle.AndroidViewModel; // need to use the application context for the workmanager
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.app.Application;
import android.net.Uri;
import android.text.TextUtils;

import com.example.background.workers.BlurWorker;
import com.example.background.workers.CleanupWorker;
import com.example.background.workers.SaveImageToFileWorker;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class BlurViewModel extends AndroidViewModel {

    private Uri mImageUri;
    private WorkManager mWorkManager;
    private LiveData<List<WorkInfo>> mSavedWorkInfo;
    private Uri mOutputUri; // final uri of the blurred picture

    public BlurViewModel(@NonNull Application application) {
        super(application);
        mWorkManager = WorkManager.getInstance(application);
        mWorkManager.pruneWork(); // for clean all job status in your codebase, since WorkManager saves all WorkInfo from previous call.
        mSavedWorkInfo = mWorkManager.getWorkInfosByTagLiveData(Constants.TAG_OUTPUT);
    }

    /**
     * Create the WorkRequest to apply the blur and save the resulting image
     * @param blurLevel The amount to blur the image
     */
    void applyBlur(int blurLevel) {
        // enqueue a OneTimeWorkRequest
        // mWorkManager.enqueue(OneTimeWorkRequest.from(BlurWorker.class));

        // enqueue a OneTimeWorkRequest with Data input
        // OneTimeWorkRequest blurRequest =
        //        new OneTimeWorkRequest.Builder(BlurWorker.class)
        //                .setInputData(createInputDataForUri())
        //                .build();
        // mWorkManager.enqueue(blurRequest);

        // chain WorkRequests, which can be executed in parallel
        // WorkContinuation continuation = mWorkManager.beginWith(OneTimeWorkRequest.from(CleanupWorker.class));

        // Ensure Unique Work
        WorkContinuation continuation = mWorkManager.beginUniqueWork(
                Constants.IMAGE_MANIPULATION_WORK_NAME,
                ExistingWorkPolicy.REPLACE, // use REPlACE because if the user decides to blur another image before the current one is finished
                                            // we want to stop the current one and start blurring the new image.
                OneTimeWorkRequest.from(CleanupWorker.class)
        );

        for (int i = 0; i < blurLevel; i++) {
            OneTimeWorkRequest.Builder blurBuilder = new OneTimeWorkRequest.Builder(BlurWorker.class);

            // Input the Uri if this is the first blur operation
            // After the first blur operation the input will be the output of previous
            // blur operations.
            if (i == 0) {
                blurBuilder.setInputData(createInputDataForUri());
            }
            continuation = continuation.then(blurBuilder.build()); // continuation.then returns a new continuation instance.
        }

        // Create charging constraint
        // this constraints meets when the AC adapter is connected, battery status is charging and the power level increases or reaches 100%
        // Testing this in Battery setting in the emulator settings
        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(true)
                .build();

        OneTimeWorkRequest save = new OneTimeWorkRequest.Builder(SaveImageToFileWorker.class)
                // .setConstraints(constraints)
                .addTag(Constants.TAG_OUTPUT) // Add a tag to WorkRequest
                .keepResultsForAtLeast(5, TimeUnit.SECONDS) // keep the Results for at least 5 seconds
                .build();
        continuation = continuation.then(save);
        // continuation = continuation.then(OneTimeWorkRequest.from(SaveImageToFileWorker.class));

        // Actually start he work
        continuation.enqueue();
    }

    private Uri uriOrNull(String uriString) {
        if (!TextUtils.isEmpty(uriString)) {
            return Uri.parse(uriString);
        }
        return null;
    }

    /**
     * Setters
     */
    void setImageUri(String uri) {
        mImageUri = uriOrNull(uri);
    }

    /**
     * Getters
     */
    Uri getImageUri() {
        return mImageUri;
    }

    private Data createInputDataForUri() {
        Data.Builder builder = new Data.Builder();
        if (mImageUri != null) {
            builder.putString(Constants.KEY_IMAGE_URI, mImageUri.toString());
        }
        return builder.build();
    }

    // Add a getter method for mSavedWorkInfo
    public LiveData<List<WorkInfo>> getOutputWorkInfo() {
        return mSavedWorkInfo;
    }

    void setOutputUri(String outputImageUri) {
        mOutputUri = uriOrNull(outputImageUri);
    }

    Uri getOutputUri() {
        return mOutputUri;
    }

    /**
     * Cancel work using the work's unique name
     */
    void cancelWork() {
        mWorkManager.cancelUniqueWork(Constants.IMAGE_MANIPULATION_WORK_NAME);
    }
}