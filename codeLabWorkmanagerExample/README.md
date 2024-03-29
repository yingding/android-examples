# Origin of this code example
This Workmanager Example is originated from Google Codelab for android-workmanager - Java
https://codelabs.developers.google.com/codelabs/android-workmanager/#0

## WorkManager Basics
* Worker: This is where you put the code for the actual work you want to perform in the background. You'll extend this class and override the doWork() method.
* WorkRequest: This represents a request to do some work. You'll pass in your Worker as part of creating your WorkRequest. When making the WorkRequest you can also specify things like Constraints on when the Worker should run.
* WorkManager: This class actually schedules your WorkRequest and makes it run. It schedules WorkRequests in a way that spreads out the load on system resources, while honoring the constraints you specify.

WorkManager Codelab
===================================

This repository contains the code for the [WorkManager Codelab](https://codelabs.developers.google.com/codelabs/android-workmanager):

Kotlin version
--------------

The Kotlin version of this codelab is available under the `Kotlin` branch of
this repository.

Introduction
------------

At I/O 2018 Google annouced [Android Jetpack](https://developer.android.com//jetpack/),
a collection of libraries, tools and architectural guidance to accelerate and simplify the
development of great Android apps. One of those libraries is the
[WorkManager library](https://developer.android.com/topic/libraries/architecture/workmanager/).
The WorkManager library provides a unified API for deferrable one-off or recurring background tasks
that need guaranteed execution. You can learn more by reading the
[WorkManager Guide](https://developer.android.com/topic/libraries/architecture/workmanager/), the
[WorkManager Reference](https://developer.android.com/reference/androidx/work/package-summary)
or doing the
[WorkManager Codelab](https://codelabs.developers.google.com/codelabs/android-workmanager).


Pre-requisites
--------------

* Android Studio 3.1 or later and you know how to use it.

* Make sure Android Studio is updated, as well as your SDK and Gradle.
Otherwise, you may have to wait for a while until all the updates are done.

* A device or emulator that runs API level 16+

You need to be solidly familiar with the Java programming language,
object-oriented design concepts, and Android Development Fundamentals.
In particular:

* Basic layouts and widgets
* Some familiarity with Uris and File I/O
* Familiarity with [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
  and [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)

Getting Started
---------------

1. [Install Android Studio](https://developer.android.com/studio/install.html),
if you don't already have it.
2. Download the sample.
2. Import the sample into Android Studio.
3. Build and run the sample.

Notes
-----

On Android Studio 3.1, when opening the project, you may see the error:

> Configuration on demand is not supported by the current version of the Android
Gradle plugin since you are using Gradle version 4.6 or above. Suggestion:
disable configuration on demand by setting `org.gradle.configureondemand=false`
in your gradle.properties file or use a Gradle version less than 4.6.

This is a known issue with the Android Gradle Plugin 3.0.x and 3.1.x. [You can
follow this workaround to fix the
issue.](https://developer.android.com/studio/known-issues#known_issues_with_the_android_gradle_plugin)

License
-------

Copyright 2018 Google, Inc.

All image and audio files (including *.png, *.jpg, *.svg, *.mp3, *.wav
and *.ogg) are licensed under the CC BY 4.0 license. All other files are
licensed under the Apache 2 license.

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements.  See the LICENSE file distributed with this work for
additional information regarding copyright ownership.  The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy of
the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.
