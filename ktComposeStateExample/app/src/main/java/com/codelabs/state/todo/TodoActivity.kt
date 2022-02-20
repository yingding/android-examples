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

package com.codelabs.state.todo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.codelabs.state.ui.StateCodelabTheme

class TodoActivity : AppCompatActivity() {

    val todoViewModel by viewModels<TodoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StateCodelabTheme {
                // Surface adds a background to the app, and configures the color of text
                Surface {
                    // TODO: build the screen in compose
                    TodoActivityScreen(todoViewModel)
                }
            }
        }
    }
}

// state hoisting allows to display a stateful UI by defining a stateless composable
@Composable
private fun TodoActivityScreen(todoViewModel: TodoViewModel) {
    /* 1. observeAsSate observes a LiveData<T> and converts it into a State<T> object
     *    so Compose can react to value changes
     * 2. listOf() is an initial value to avoid possible null results before the LiveData
     *    is initialized, if it is not passed items would be List<TodoItem>? which is nullable
     * 3. by is the property delegate syntax in Kotlin, it lets us automatically unwrap the
     *    State<List<TodoItem>> from observeAsSate into a regular List<TodoItem>
     */
    val items: List<TodoItem> by todoViewModel.todoItems.observeAsState(listOf())
    // val items = listOf<TodoItem>()
    TodoScreen(
        items = items,
        onAddItem = { todoViewModel.addItem(it) },
        onRemoveItem = todoViewModel::removeItem // same as { todoViewModel.removeItem(it) }
    )
}
