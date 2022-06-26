/*
 * Copyright (C) 2020 The Android Open Source Project
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

package com.example.android.hilt.data

import android.os.Handler
import android.os.Looper
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data manager class that handles data manipulation between the database and the UI.
 *
 * Annotate the constructor in kotlin with constructor key words, so that hilt will create a
 * new instance of this class, every time when the hilt container need an instance of this class.
 *
 * The information that tells Hilt how to provide instances of different types are also called bindings.
 *
 * In additionally, annotate this class to be singleton for the hilt application.
 * Annotate the scope of this component with @Singleton,
 * https://developer.android.com/training/dependency-injection/hilt-android#component-scopes
 *
 * The application singleton has a transitive dependency of Interface LogDao,
 * since @Inject annotation only works with constructor. A hilt module will be needed to bind Interface in hilt
 */
@Singleton
class LoggerLocalDataSource @Inject constructor(private val logDao: LogDao) {


    private val executorService: ExecutorService = Executors.newFixedThreadPool(4)
    private val mainThreadHandler by lazy {
        Handler(Looper.getMainLooper())
    }

    fun addLog(msg: String) {
        executorService.execute {
            logDao.insertAll(
                Log(
                    msg,
                    System.currentTimeMillis()
                )
            )
        }
    }

    fun getAllLogs(callback: (List<Log>) -> Unit) {
        executorService.execute {
            val logs = logDao.getAll()
            mainThreadHandler.post { callback(logs) }
        }
    }

    fun removeLogs() {
        executorService.execute {
            logDao.nukeTable()
        }
    }
}
