# Introduction

this code repository is based on the [Kotlin Coroutines codelab](https://developer.android.com/codelabs/kotlin-coroutines#0). The original code is update to:

* Kotlin Version: 1.5.21
* Jetpack ViewBinding
* All no alpha Libs update till 21.August
* Added yield() suspend function to allow coroutines in Dispatcher.IO scope to be cancellable
* Fixed Instrumented Test of WorkManager and Coroutines not run issue

## Current Learning Stage
https://developer.android.com/codelabs/kotlin-coroutines#11

## Useful Resources:
* [Interactive Kotlin Online Doc](https://kotlinlang.org/docs/cancellation-and-timeouts.html#making-computation-code-cancellable) for Kotlin coroutines cancellation and timeouts.

## Kotlin Syntax

* double bangs: T!!, converts a unsafe nullable type to a non-null type T, if the object is null, it throws an Exception
* platform type: T!, it indicates T or T?, kotlin can not known the type and let the developer decide what to do.

## Modules
* start: the original start code template for your own exercises
* app: contains all the improved code results with all non-alpha libs till 23.Aug.2021
* finished_code: the original google codelab results module.

you can compare `app` module with `finished_code` module to see how the code have been changed from 2019 as google published this codelab and how you shall use coroutines at the time of 23.Aug.2021


<!-- reference style link
non-alpha libs till [last edit][1]
[1]: 23-Aug-2021
Note the reference style link just make a link, it doesn't replace the text

https://stackoverflow.com/questions/24580042/github-markdown-are-macros-and-variables-possible/26196818#26196818
https://www.brianchildress.co/variables-in-markdown/
-->