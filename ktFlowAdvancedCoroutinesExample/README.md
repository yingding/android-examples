# About this project

this project is a folk from the codelab [`Advanced coroutines with Kotlin Flow and LiveData`](https://developer.android.com/codelabs/advanced-kotlin-coroutines#0)

This project codes are updated and modified by the author of this project.

## Changelog
* Updated kotlin version to 1.7.10
* Updated `MenuHost`, `MenuHost.addMenuProvider`
* Added app folder for the author of this project to practice

## Learning progress
* https://developer.android.com/codelabs/advanced-kotlin-coroutines#5

## LiveData
* Difference between emit() and emitSource(): https://stackoverflow.com/questions/58546944/what-is-the-difference-between-emit-and-emitsource-with-livedata-as-in-real/58950866#58950866

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

# Reference:
* Original codelab `Learn advanced coroutines with Kotlin Flow and LiveData` https://developer.android.com/codelabs/advanced-kotlin-coroutines#0