# Introduction

this code repository is based on the (Kotlin Coroutines codelab)[https://developer.android.com/codelabs/kotlin-coroutines#0]. The original code is update to:

* Kotlin Version: 1.5.21
* Jetpack ViewBinding
* All no alpha Libs update till 21.August
* Added yield() suspend function to allow coroutines in Dispatcher.IO scope to be cancellable

## Current Learning Stage
https://developer.android.com/codelabs/kotlin-coroutines#8

## Useful Resources:
* (Interactive Kotlin Online Doc)[https://kotlinlang.org/docs/cancellation-and-timeouts.html#making-computation-code-cancellable] for Kotlin coroutines cancellation and timeouts.

## Kotlin Syntax

* double bangs: T!!, converts a unsafe nullable type to a non-null type T, if the object is null, it throws an Exception
* platform type: T!, it indicates T or T?, kotlin can not known the type and let the developer decide what to do.