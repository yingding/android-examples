# About this project
this project is a copy of google code lab from: https://developer.android.com/codelabs/jetpack-compose-testing

This project codes are updated and modified by the author of this project.

## Folders
* `app` folder is the playground folder of the author learning from this codelab
* `start` folder is the original scaffold module to start coding this codelab

## Learning progress
* https://developer.android.com/codelabs/jetpack-compose-testing#7

## What to do next
* https://developer.android.com/codelabs/jetpack-compose-testing#7

## Compose Testing Cheatsheet
* https://developer.android.com/jetpack/compose/testing-cheatsheet

## Testing Package Reference Doc
* https://developer.android.com/reference/kotlin/androidx/compose/ui/test/package-summary

## Enable Animation Preview
Android Studio -> Preferences -> Experimental -> Jetpack Compose -> Enable Animation Preview

## Testing Deep Link with ADB
```console
adb shell am start -d "rally://accounts/Checking" -a android.intent.action.VIEW
```
this start an intent with deep link data `rally://accounts/Checking` and action `android.intent.action.VIEW`

## Testing Navigation in Compose
* Compose Testing docs: https://developer.android.com/jetpack/compose/testing
* Compose Testing codelab: https://developer.android.com/codelabs/jetpack-compose-testing
* Advanced testing of navigation code using the `TestNavHostController`: https://developer.android.com/guide/navigation/navigation-testing

# (original README content of codelab) Testing in Jetpack Compose Codelab

This folder contains the source code for the
[Testing in Jetpack Compose Codelab ](https://developer.android.com/codelabs/jetpack-compose-testing)
codelab.

## License
```
Copyright 2021 The Android Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
