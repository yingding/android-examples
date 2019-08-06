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