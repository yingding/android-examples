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
hw.sensor.heart_rate=yes
```
and cold restart your Wear OS API 28 AVD to activate the heart rate virual sensor manually.
