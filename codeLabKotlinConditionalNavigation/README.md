# Introduction

this example is based on Google Kotlin Android Codelab "Android Login with FirebaseUI" and "Android Conditional Navigation with Login"

## Changes made by the author of this repository

Author of the repository has made the following changes to the original codes:
 
* upgrade all libs into currently stable ones (time of creation July 2020)
* added start, final, app1 and app2 module (start is the start module to play with the code, final is the solution module, app1 -- Login -- and app2 -- conditional Login -- modules are used by the author for self study )
* adding the central rootProject variables in root level build.gradle file to enable quick SDK upgrade for all modules
* firebase project integration file `google-services.json` are NOT included, please use your own firebase config json file by following the [instruction](https://codelabs.developers.google.com/codelabs/advanced-android-kotlin-training-login/#3)

## Author's self study progress for App Module

Step 1 with Firebase UI (base module: **app1**)

* https://codelabs.developers.google.com/codelabs/advanced-android-kotlin-training-login/#8

Step 2 with Conditional Navigation with Login (base module: **app2**)

* https://codelabs.developers.google.com/codelabs/advanced-android-kotlin-training-login-navigation/index.html#0


# Implementing Login with Navigation on Android with FirebaseUI

This is part of the Advanced Android in Kotlin course. You'll get
the most value out of this course if you work through the codelabs in sequence,
but it is not mandatory. All the course codelabs are listed on the [Advanced Android
in Kotlin codelabs landing page](https://codelabs.developers.google.com/codelabs/advanced-android-kotlin-training-welcome).

This repository is to support the [Android Conditional Navigation with Login codelab](https://codelabs.developers.google.com/codelabs/advanced-android-kotlin-training-login-navigation)
While you can download the starter code for this codelab if you haven't done the previous codelab,
you may find it beneficial to complete the [Implementing Login on Android with FirebaseUI codelab](https://codelabs.developers.google.com/codelabs/advanced-android-kotlin-training-login) first.

## Setup

You will need to register this app with Firebase for it to work, if you haven’t completed the [Implementing Login on Android with FirebaseUI codelab](https://codelabs.developers.google.com/codelabs/advanced-android-kotlin-training-login), follow these steps to [create a firebase project](https://codelabs.developers.google.com/codelabs/advanced-android-kotlin-training-login/#3) and register the app to it.


## License

Copyright 2019 Google, Inc.

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements.  See the NOTICE file distributed with this work for
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

