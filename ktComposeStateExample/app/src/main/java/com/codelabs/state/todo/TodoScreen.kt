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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelabs.state.util.generateRandomTodoItem
import kotlin.random.Random

/**
 * Stateless component that is responsible for the entire todo screen.
 *
 * @param items (state) list of [TodoItem] to display
 * @param onAddItem (event) request an item be added
 * @param onRemoveItem (event) request an item be removed
 */
@Composable
fun TodoScreen(
    items: List<TodoItem>,
    onAddItem: (TodoItem) -> Unit,
    onRemoveItem: (TodoItem) -> Unit
) {
    Column {
        // add TodoItemInputBackground and TodoItem at the top of TodoScreen
        TodoItemInputBackground(elevate = true, modifier = Modifier.fillMaxWidth()) {
            TodoItemEntryInput(onItemComplete = onAddItem)
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(top = 8.dp)
        ) {
            items(items = items) {
                TodoRow(
                    todo = it,
                    onItemClicked = { onRemoveItem(it) },
                    modifier = Modifier.fillParentMaxWidth()
                )
            }
        }

        // For quick testing, a random item generator button
        Button(
            onClick = { onAddItem(generateRandomTodoItem()) },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Text("Add random item")
        }
    }
}

/**
 * Stateless composable that displays a full-width [TodoItem].
 *
 * @param todo item to show
 * @param onItemClicked (event) notify caller that the row was clicked
 * @param modifier modifier for this element
 */
@Composable
fun TodoRow(
    todo: TodoItem,
    onItemClicked: (TodoItem) -> Unit,
    modifier: Modifier = Modifier,
    iconAlpha: Float = remember(todo.id) { randomTint() }
) {
    Row(
        modifier = modifier
            .clickable { onItemClicked(todo) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(todo.task)
        /* adding random icon alpha
         * remember gives a composable function memory
         * A value computed by remember will be stored in the composition tree, and only be recomputed
         * if the keys to remember change.
         * You can think of remember as giving storage for a single object to a function the same way a
         * private val property does in an object
         */
        // val iconAlpha = remember(todo.id) {randomTint()}
        Icon(
            imageVector = todo.icon.imageVector,
            tint = LocalContentColor.current.copy(alpha = iconAlpha),
            contentDescription = stringResource(id = todo.icon.contentDescription)
        )
    }
}

/**
 * A stateful composable is a composable that owns a piece of state
 * that it can change over time
 */
@Composable
fun TodoInputTextField(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier) {
    // val (text, setText) = remember { mutableStateOf("") }
    TodoInputText(text, onTextChange, modifier)
}

/**
 * stateful compose
 */
@Composable
fun TodoItemEntryInput(onItemComplete: (TodoItem) -> Unit) {
    // onItemComplete is an event will fire when an item is completed by the user

    // setText and setIcon are the event function passed to InputTextField and AnimatedIconRow
    // text, icon are the internal state.
    val (text, setText) = remember { mutableStateOf("") }
    val (icon, setIcon) = remember { mutableStateOf(TodoIcon.Default) }
    val iconsVisible = text.isNotBlank()

    val submit = {
        onItemComplete(TodoItem(text, icon)) // send onItemComplete event up and use the icon state
        // call the setIcon event for the AnimatedIconRow to reset icon
        setIcon(TodoIcon.Default) // reset the icon
        // call the setText event to trigger the onTextChange event in TodoInputTextField
        setText("") // clear the internal text
    }

    // 1. Alt + Enter on the function call and select "Add names to call arguments"
    // this way you can added arg names automatically
    TodoItemInput(
        text = text,
        onTextChange = setText,
        icon = icon,
        setIcon = setIcon,
        submit = submit,
        iconsVisible = iconsVisible
    )
}

/**
 * stateless compose
 * Extracting a stateless composable from a stateful composable makes it easier
 * to reuse the UI in different locations.
 */
@Composable
fun TodoItemInput(
    text: String,
    onTextChange: (String) -> Unit,
    icon: TodoIcon,
    setIcon: (TodoIcon) -> Unit,
    submit: () -> Unit,
    iconsVisible: Boolean
) {
    Column {
        Row(
            Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            // TodoInputTextField() is the same as TodoInputText without local state
            TodoInputText(
                text = text,
                onTextChange = onTextChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                onImeAction = submit // pass the submit callback to TodoInputText
            )
            TodoEditButton(
                onClick = submit, // pass the submit callback to TdoEditButton
                text = "Add",
                modifier = Modifier.align(Alignment.CenterVertically),
                enabled = text.isNotBlank() // enable if text is not blank
            )
        }
        if (iconsVisible) {
            AnimatedIconRow(icon, setIcon, Modifier.padding(top = 8.dp))
        } else {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


/**
 * A side-effect is any change that's visible outside of a composable function.
 * Recomposing a composable should be side-effect free.
 *
 * For example, updating state in a ViewModel, calling Random.nextInt(), or writing to database are all side-effects
 */
private fun randomTint(): Float {
    return Random.nextFloat().coerceIn(0.3f, 0.9f)
}

@Preview(showBackground = true)
@Composable
fun PreviewTodoScreen() {
    val items = listOf(
        TodoItem("Learn compose", TodoIcon.Event),
        TodoItem("Take the codelab"),
        TodoItem("Apply state", TodoIcon.Done),
        TodoItem("Build dynamic UIs", TodoIcon.Square)
    )
    TodoScreen(items, {}, {})
}

@Preview(showBackground = true)
@Composable
fun PreviewTodoRow() {
    val todo = remember { generateRandomTodoItem() }
    TodoRow(todo = todo, onItemClicked = {}, modifier = Modifier.fillMaxWidth())
}

@Preview(showBackground = true)
@Composable
fun PreviewTodoItemInput() = TodoItemEntryInput(onItemComplete = {})