# Introduction

this project is an example of theme attribute can not be used in vector image any more in Android Studio 4.1 after the android platform update.
The same issue is described at https://stackoverflow.com/questions/64441692/android-studio-4-1-theme-attributes-in-vector-drawables-cause-java-lang-illegal

# Root Cause

this issue occurs if the theme attribute such as color attribute is used `?attr/colorPrimary` is used in vector image for SDK < 24.

# Solution
Reference: https://medium.com/androiddevelopers/using-vector-assets-in-android-apps-4318fd662eb9

1. Adding AndroidX vector support in your base module's (app's) build.gradle`
```console
android {
    defaultConfig {
        vectorDrawables.useSupportLibrary = true
    }
}
```

2. using `app:srcCompat` instead of `android:src`
