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

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.kotlincoroutines.fakes.MainNetworkFake
import com.example.android.kotlincoroutines.fakes.TitleDaoFake
import com.example.android.kotlincoroutines.main.utils.MainCoroutineScopeRule
import com.example.android.kotlincoroutines.main.utils.getValueForTest
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {
    // a rule is a way to run code before and after the execution of a test in JUnit.
    @kotlinx.coroutines.ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()
    // MainCoroutineScopeRule() lets you pause, resume, or control the execution
    // of coroutines that are launched on the Dispatchers.Main
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // late initialization:
    // https://0xalihn.medium.com/kotlin-var-val-lateinit-lazy-get-when-to-use-what-bd50200b0a38
    // Not allocate memory until initialized
    lateinit var subject: MainViewModel

    @Before
    fun setup() {
        // initialization
        subject = MainViewModel(
                TitleRepository(
                        MainNetworkFake("OK"),
                        TitleDaoFake("initial")
                ))
    }

    @Test
    fun whenMainClicked_updatesTaps() {
        // TODO: Write this
        subject.onMainViewClicked()
        Truth.assertThat(subject.taps.getValueForTest()).isEqualTo("0 taps")
        // virtual time
        // add in vm option in run config: -Xopt-in=kotlin.RequiresOptIn
        @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
        coroutineScope.advanceTimeBy(1_000)
        Truth.assertThat(subject.taps.getValueForTest()).isEqualTo("1 taps")
    }
}