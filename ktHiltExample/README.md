# About this project

This project is following the Google Codelabs example of Android Hilt

* https://developer.android.com/codelabs/android-hilt?hl=en#0

all dependency libs are updated to the latest version by the author of this repo at the time of writing.

## Folders
* `app` folder is the playground folder of the author learning from this codelab
* `start` folder is the original scaffold module to start coding this codelab

## Progress so far

* https://developer.android.com/codelabs/android-hilt?hl=en#2

## Reference:
* Fundamentals of dependency injection in Android: https://developer.android.com/training/dependency-injection
* Manual dependency injection in Android: https://developer.android.com/training/dependency-injection/manual


## Use spotless
```
./gradlew build
./gradlew spotlessApply
./gradlew build
```


# (Original Codelab Content)Using Hilt in your Android app

This folder contains the source code for the "Using Hilt in your Android app" codelab.

The codelab is built in multiple GitHub branches:
* `main` is the codelab's starting point.
* `solution` contains the solution to this codelab.


# Introduction
Dependency injection is a technique widely used in programming and well suited
to Android development. By following the principles of dependency injection, you
lay the groundwork for a good app architecture.

Implementing dependency injection provides you with the following advantages:
* Reusability of code.
* Ease of refactoring.
* Ease of testing.


# Pre-requisites
* Experience with Kotlin syntax.
* You understand Dependency Injection.

# Getting Started
1. Install Android Studio, if you don't already have it.
2. Download the sample.
3. Import the sample into Android Studio.
4. Build and run the sample.


# Comparison between different branches
* [Full codelab comparison](https://github.com/googlecodelabs/android-hilt/compare/main...solution)


# License

```
Copyright (C) 2020 The Android Open Source Project

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