/*
 * Copyright (C) 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.kotlincoroutines.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.android.kotlincoroutines.util.BACKGROUND
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield

/**
 * TitleRepository provides an interface to fetch a title or request a new one be generated.
 *
 * Repository modules handle data operations. They provide a clean API so that the rest of the app
 * can retrieve this data easily. They know where to get the data from and what API calls to make
 * when data is updated. You can consider repositories to be mediators between different data
 * sources, in our case it mediates between a network API and an offline database cache.
 */
class TitleRepository(val network: MainNetwork, val titleDao: TitleDao) {

    /**
     * [LiveData] to load title.
     *
     * This is the main interface for loading a title. The title will be loaded from the offline
     * cache.
     *
     * Observing this will not cause the title to be refreshed, use [TitleRepository.refreshTitleWithCallbacks]
     * to refresh the title.
     */
    val title: LiveData<String?> = titleDao.titleLiveData.map { it?.title }

    // TODO: Add coroutines-based `fun refreshTitle` here

    /**
     * To switch between any dispatcher, coroutines uses withContext. Calling withContext
     * switches to the other dispatcher just for the lambda then comes back to the dispatcher
     * that called it with the result of that lambda.
     *
     * By default, Kotlin coroutines provides three Dispatchers: Main, IO, and Default.
     * The IO dispatcher is optimized for IO work like reading from the network or disk,
     * while the Default dispatcher is optimized for CPU intensive tasks.
     */
//    suspend fun refreshTitle() {
//        // interact with *blocking* network and IO calls from a coroutine
//        withContext(Dispatchers.IO) {
//            // Response<String!>! single bang, platform types: T! T or T?
//            // https://stackoverflow.com/questions/43826699/single-exclamation-mark-in-kotlin
//            val result = try {
//                // Make network request using a blocking call
//                network.fetchNextTitle().execute()
//            } catch (cause: Throwable) {
//                // If the network throws an exception, inform the caller
//                throw TitleRefreshError("Unable to refresh title", cause)
//            }
//
//            /* use a suspend function yield() so that the following code can be cancelled
//             * or if (isActive) to check the coroutine state actively.
//             * https://kotlinlang.org/docs/cancellation-and-timeouts.html#making-computation-code-cancellable
//             */
//            yield()
//
//            if (result != null && result.isSuccessful) {
//                // Save it to database
//                titleDao.insertTitle(Title(result.body()!!))
//            } else {
//                // If it's not successful, inform the callback
//                throw TitleRefreshError("Unable to refresh title", null)
//            }
//        }
//    }

    /**
     * Both Room and Retrofit use a custom dispatcher and do not use Dispatchers.IO.
     *
     * Room will run coroutines using the default query and transaction Executor that's configured.
     * Retrofit will create a new Call object under the hood, and call enqueue on it to send the request
     * asynchronously.
     */
    suspend fun refreshTitle() {
        try {
            // Make network request using a blocking call with suspend function on the Dispatcher.Main
            val result = network.fetchNextTitle()
            titleDao.insertTitle(Title(result))
        } catch (cause: Throwable) {
            // If anything throws an exception, inform the caller
            throw TitleRefreshError("Unable to refresh title", cause)
        }
    }

    /**
     * Refresh the current title and save the results to the offline cache.
     *
     * This method does not return the new title. Use [TitleRepository.title] to observe
     * the current tile.
     */
//    fun refreshTitleWithCallbacks(titleRefreshCallback: TitleRefreshCallback) {
//        // This request will be run on a background thread by retrofit
//        // ExecutorService.submit returns a Future object, in this case execute will also do
//        // call with last arg lambda function call
//        BACKGROUND.execute {
//        // BACKGROUND.submit {
//            try {
//                // Make network request using a blocking call
//                val result = network.fetchNextTitle().execute()
//                if (result.isSuccessful) {
//                    // Save it to database
//                    titleDao.insertTitle(Title(result.body()!!))
//                    // Inform the caller the refresh is completed
//                    titleRefreshCallback.onCompleted()
//                } else {
//                    // If it's not successful, inform the callback of the error
//                    titleRefreshCallback.onError(
//                            TitleRefreshError("Unable to refresh title", null))
//                }
//            } catch (cause: Throwable) {
//                // If anything throws an exception, inform the caller
//                titleRefreshCallback.onError(
//                        TitleRefreshError("Unable to refresh title", cause))
//            }
//        }
//    }

} // end of class

/**
 * Thrown when there was a error fetching a new title
 *
 * @property message user ready error message
 * @property cause the original cause of this exception
 */
class TitleRefreshError(message: String, cause: Throwable?) : Throwable(message, cause)

interface TitleRefreshCallback {
    fun onCompleted()
    fun onError(cause: Throwable)
}
