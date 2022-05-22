# About this project
this project is a copy of google code lab from: https://developer.android.com/codelabs/jetpack-compose-advanced-state-side-effects
The original google codelab is created by AnroidDevRel Manuel Vivo (https://medium.com/@manuelvicnt)

This project codes are updated and modified by the author of this project.
* Dependencies are updated in `/buildSrc/src/main/java/com/example/crane/buildsrc/Dependencies.kt` file

### Folders
* `app` folder is the playground folder of the author learning from this codelab
* `start` folder is the original scaffold module to start coding this codelab

### Learning progress
* https://developer.android.com/codelabs/jetpack-compose-advanced-state-side-effects#1

### Use spotless
```
./gradlew build
./gradlew spotlessApply
./gradlew build
```
Reference:
* https://github.com/diffplug/spotless/tree/main/plugin-gradle
* https://github.com/diffplug/spotless



# (original README content of codelabe) Advanced State in Jetpack Compose Codelab

This folder contains the source code for the
[Advanced State in Jetpack Compose Codelab](https://developer.android.com/codelabs/jetpack-compose-advanced-state-side-effects)
codelab.

The project is built in multiple git branches:
* `main` – the starter code for this project, you will make changes to this to complete the codelab
* `end` – contains the solution to this codelab

## [Optional] Google Maps SDK setup

Seeing the city on the MapView is not necessary to complete the codelab. However, if you want
to get the MapView to render on the screen, you need to get an API key as
the [documentation says](https://developers.google.com/maps/documentation/android-sdk/get-api-key),
and include it in the `local.properties` file as follows:

```
google.maps.key={insert_your_api_key_here}
```

When restricting the Key to Android apps, use `androidx.compose.samples.crane` as package name, and
`A0:BD:B3:B6:F0:C4:BE:90:C6:9D:5F:4C:1D:F0:90:80:7F:D7:FE:1F` as SHA-1 certificate fingerprint.

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
