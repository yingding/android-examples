# Emulated Heartrate virtual sensor with API 28

The Wear OS Image API 28 Revision 5 on MacOSX doesn't activate the virtual sensor on Emulator >= 30.4.5 properly.
It is no use to recreate the AVD of Wear OS images.

1. Use
```console
adb emu avd path
```
to figure out the AVD virtual device path.

2. Add to the config.ini of your wear os AVD folder with the following additional config
```console
hw.sensors.heart_rate=yes
```
and cold restart your Wear OS API 28 AVD to activate the heart rate virual sensor manually.

# Wear OS by Google (Dev Sources)
## Wear OS Docs:
* Wear OS Dev Page: https://developer.android.com/wear
* Wear OS app quality: https://developer.android.com/docs/quality-guidelines/wear-app-quality
* Get started with Wear OS: https://developer.android.com/training/wearables
* Distribute to Wear OS: https://developer.android.com/distribute/best-practices/launch/distribute-wear
* Wear OS release notes: https://developer.android.com/wear/releases
* Wear Library Doc: https://developer.android.com/jetpack/androidx/releases/wear
* Health Service on Wear OS Doc: https://developer.android.com/training/wearables/health-services

## Github Androidx Source Code
* Androidx Wear Source Code: https://github.com/androidx/androidx/tree/androidx-main/wear

## Wear OS Sample Code:
* Wear OS Samples Repository: https://github.com/android/wear-os-samples
* Health Service: https://github.com/android/health-samples

## Issue Tracker
* Wear OS Issue Tracker: https://issuetracker.google.com/issues?q=componentid:460965