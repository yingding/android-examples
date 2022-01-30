package com.example.ktcomposelayoutsexample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.example.ktcomposelayoutsexample.ui.theme.KtComposeLayoutsExampleTheme

@Composable
fun SimpleList() {
    // We save the scrolling position with this state that can also
    // be used to programmatically scroll the list
    val scrollState = rememberScrollState()

    Column(Modifier.verticalScroll(scrollState)){
        repeat(100) {
            Text("Item #$it")
        }
    }
}

@Composable
fun LazyList() {
    // We save the scrolling position with this state that can also
    // be used to programmatically scroll the list
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState) {
        // DSL
        items(100) {
            Text("Item #$it")
        }
    }
}

@Preview(
    showBackground = true,
    heightDp = 320,
    device = Devices.NEXUS_5
)
@Composable
fun SimpleListPreview() {
    KtComposeLayoutsExampleTheme {
        SimpleList()
    }
}

@Preview(
    showBackground = true,
    heightDp = 320,
    device = Devices.NEXUS_5
)
@Composable
fun LazyListPreview() {
    KtComposeLayoutsExampleTheme {
        LazyList()
    }
}