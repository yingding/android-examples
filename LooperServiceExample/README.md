# References

the code example in this project are stimulated by the following web references:

* Using a Looper in a Service https://stackoverflow.com/questions/19825879/using-a-looper-in-a-service-is-the-same-as-using-a-separate-thread
* A journey on the Android Main Thread https://medium.com/square-corner-blog/a-journey-on-the-android-main-thread-psvm-55b2238ace2b
* Android Doc for IntentService https://developer.android.com/reference/android/app/IntentService.html
* Android Doc for Services https://developer.android.com/guide/components/services.html#ExtendingService

# Process and Handler Thread
* Thread: https://developer.android.com/guide/components/processes-and-threads.html#Threads

# Scheduled Job for API Level 26+
 
 Due to the background execution limit above API level 26 (https://developer.android.com/about/versions/oreo/background.html). 
 please consider to use a scheduled job instead (https://developer.android.com/guide/background)
 
# Import Material Icons to the RES folder

* Right-click the res folder and select New > Vector Asset
* Click on the clip-art button to select "Material Icon"
* Use search to search for the name of the icons in category "all"
* select the color for the icon
* select ok

Reference: https://stackoverflow.com/questions/28684759/import-material-design-icons-into-an-android-project

# FAB design

Designing FAB with 24x24 dp 

Reference: https://stackoverflow.com/questions/27484126/adjust-icon-size-of-floating-action-button-fab

# Use VectorDrawableCompat
You need to make two modifications to your project. 

* set android.defaultConfig.vectorDrawables.useSupportLibrary = true in your module's build.gradle file in section defaultConfig (https://stackoverflow.com/questions/41226382/using-vector-drawable-compat). 
* use app:srcCompat instead of android:src to refer to vector drawables.

# Further thoughts

If press the fab 3 times you got the output in Logcat
```console
2019-08-06 18:04:59.808 8441-8465/com.example.looperserviceexample V/SyncTCPService: arg1 is: 1 current job id is: 0
2019-08-06 18:05:04.849 8441-8465/com.example.looperserviceexample V/SyncTCPService: arg1 is: 2 current job id is: 0
2019-08-06 18:05:09.874 8441-8465/com.example.looperserviceexample V/SyncTCPService: arg1 is: 3 current job id is: 0
2019-08-06 18:05:14.905 8441-8465/com.example.looperserviceexample V/SyncTCPService: arg1 is: 1 current job id is: 1
2019-08-06 18:05:19.946 8441-8465/com.example.looperserviceexample V/SyncTCPService: arg1 is: 2 current job id is: 1
2019-08-06 18:05:24.988 8441-8465/com.example.looperserviceexample V/SyncTCPService: arg1 is: 3 current job id is: 1
2019-08-06 18:05:30.002 8441-8465/com.example.looperserviceexample V/SyncTCPService: arg1 is: 1 current job id is: 2
2019-08-06 18:05:35.043 8441-8465/com.example.looperserviceexample V/SyncTCPService: arg1 is: 2 current job id is: 2
2019-08-06 18:05:40.058 8441-8465/com.example.looperserviceexample V/SyncTCPService: arg1 is: 3 current job id is: 2
2019-08-06 18:05:45.070 8441-8465/com.example.looperserviceexample V/SyncTCPService: arg1 is: 1 current job id is: 3
2019-08-06 18:05:50.096 8441-8465/com.example.looperserviceexample V/SyncTCPService: arg1 is: 2 current job id is: 3
2019-08-06 18:05:55.137 8441-8465/com.example.looperserviceexample V/SyncTCPService: arg1 is: 3 current job id is: 3
2019-08-06 18:06:00.138 8441-8465/com.example.looperserviceexample V/SyncTCPService: arg1 is: 1 current job id is: 4
2019-08-06 18:06:05.181 8441-8465/com.example.looperserviceexample V/SyncTCPService: arg1 is: 2 current job id is: 4
2019-08-06 18:06:10.196 8441-8465/com.example.looperserviceexample V/SyncTCPService: arg1 is: 3 current job id is: 4
2019-08-06 18:06:10.206 8441-8441/com.example.looperserviceexample V/SyncTCPService: Service destroyed!
```

this means the onStartCommand is called 3 times, that is what the arg1 params is outputing. The job id are the times HandleMessage are called.
Since there is only one Looper, the handleMessage method are executed sequentially.

Use two different type of SerivceHandler to asynchronously start a work and also to asynchronously call finish current work and start new work.
