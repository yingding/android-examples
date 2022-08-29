# About this project

this project is a folk from the codelab [`Advanced coroutines with Kotlin Flow and LiveData`](https://developer.android.com/codelabs/advanced-kotlin-coroutines#0)

This project codes are updated and modified by the author of this project.

## Changelog
* Updated kotlin version to 1.7.10
* Updated `MenuHost`, `MenuHost.addMenuProvider`
* Added app folder for the author of this project to practice

## Learning progress
* https://developer.android.com/codelabs/advanced-kotlin-coroutines#12

## LiveData
* Difference between emit() and emitSource(): https://stackoverflow.com/questions/58546944/what-is-the-difference-between-emit-and-emitsource-with-livedata-as-in-real/58950866#58950866

## Kotlin coroutines Cancellation and timeouts
* https://kotlinlang.org/docs/cancellation-and-timeouts.html

## Declarative API
```console
Declarative is an API style that means describing what your program should do instead of how to do it.
One of the more commonly known declarative languages is SQL, which allows developers to express
what they would like the database to query instead of how to perform the query.
```

## Retrofit

Refer to the `NetworkService.kt`:
```
class NetworkService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    ...
```

Note: \
LiveData Source means the object from where livedata gets it's value

## Introducing Flow

```
By default, a Flow will restart from the top every time a terminal operator is applied.
This is important if the Flow performs expensive work, such as making a network request.
```
* https://developer.android.com/codelabs/advanced-kotlin-coroutines#7
```
Flow supports structured concurrency

Because a flow allows you to consume values only with terminal operators, it can support structured concurrency.
When the consumer of a flow is cancelled, the entire Flow is cancelled. Due to structured concurrency,
it is impossible to leak a coroutine from an intermediate step.
```
* https://developer.android.com/codelabs/advanced-kotlin-coroutines#8



# Reference:
* Original codelab `Learn advanced coroutines with Kotlin Flow and LiveData` https://developer.android.com/codelabs/advanced-kotlin-coroutines#0