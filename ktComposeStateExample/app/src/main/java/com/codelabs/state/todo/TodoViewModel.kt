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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codelabs.state.util.generateRandomTodoItem

class TodoViewModel : ViewModel() {

    // remove the LiveData and replace it with a mutableStateListOf
    // private var _todoItems = MutableLiveData(listOf<TodoItem>())
    // val todoItems: LiveData<List<TodoItem>> = _todoItems

    // state: todoItems, MutableList<E> is observable for compose
    // SnapshotStateList class closely implements the same semantics as ArrayList.
    var todoItems: SnapshotStateList<TodoItem> = mutableStateListOf<TodoItem>()
        private set // set is restricted only visible inside the ViewModel

    fun addItem(item: TodoItem) {
        // _todoItems.value = _todoItems.value!! + listOf(item)
        todoItems.add(item)
    }

    fun removeItem(item: TodoItem) {
        // _todoItems.value = _todoItems.value!!.toMutableList().also { it.remove(item) }
        todoItems.remove(item)
        onEditDone() // don't keep the editor open when removing items
    }

    // private state
    private var currentEditPosition by mutableStateOf(-1)
    // for state<T> transformation to work, the state must be read from a State<T> object
    // If the currentEditPosition is defined as a regular Int, compose would not be able
    // to observe changes to it.

    // state: Editor
    val currentEditItem: TodoItem?
        get() = todoItems.getOrNull(currentEditPosition)
    // whenever a composable calls currentEditItem, it will observe changes to both todoItems
    // and currentEditPosition. If either change, the composable will call the getter again to get the new value.

    /*
     * Editor events
     */
    // event: onEditItemSelected
    fun onEditItemSelected(item: TodoItem) {
        currentEditPosition = todoItems.indexOf(item)
    }

    // event: onEditDone
    fun onEditDone() {
        currentEditPosition = -1
    }

    // event: onEditItemChange, update the item
    fun onEditItemChange(item: TodoItem) {
        val currentItem = requireNotNull(currentEditItem)
        require(currentItem.id == item.id) {
            "You can only change an item with the same id as currentEditItem"
        }

        todoItems[currentEditPosition] = item
    }

}
