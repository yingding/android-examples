package com.example.ktcomposelayoutsexample

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.ktcomposelayoutsexample.ui.theme.KtComposeLayoutsExampleTheme
import kotlinx.coroutines.launch

@Composable
fun SimpleList() {
    // We save the scrolling position with this state that can also
    // be used to programmatically scroll the list
    val scrollState = rememberScrollState()

    Column(Modifier.verticalScroll(scrollState)) {
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

@Composable
fun ImageListItem(index: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
       Image(
           painter = rememberImagePainter(
               data = "https://developer.android.com/images/brand/Android_Robot.png"
           ),
           contentDescription = "Android Logo",
           modifier = Modifier.size(50.dp)
       )
       Spacer(Modifier.width(10.dp))
       Text("Item #$index", style = MaterialTheme.typography.subtitle1)
    }
}

@Composable
fun ImageList() {
    // We save the scrolling position with this state
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState) {
        items(100) {
            ImageListItem(it)
        }
    }
}

@Composable
fun ScrollingList() {
    val listSize = 100
    // We save the scrolling position with this state
    val scrollState = rememberLazyListState()
    // We save the coroutine scope where our animated scroll will be executed
    val coroutineScope = rememberCoroutineScope()

    Column {


        // Button control the scroll
        // use the same weight to get the button evenly distributed
        Row {
            Button(modifier = Modifier.weight(1F),
                onClick = {
                coroutineScope.launch {
                    // 0 is the first item idex
                    scrollState.animateScrollToItem(0)
                }
            }) {
                Text("Scroll to the top")
            }
            Spacer(modifier = Modifier.size(8.dp))
            Button(modifier = Modifier.weight(1F),
                onClick = {
                coroutineScope.launch {
                    // listSize -1 is the last index of the list
                    scrollState.animateScrollToItem(listSize - 1)
                }
            }) {
                Text("Scroll to the end")
            }
        }

        // List
        LazyColumn(state = scrollState) {
            items(100) {
                ImageListItem(it)
            }
        }
    }
}

@Preview(
    showBackground = true,
    heightDp = 320,
    widthDp = 240,
)
@Composable
fun ScrollingListPreview() {
    KtComposeLayoutsExampleTheme {
        ScrollingList()
    }
}

@Preview(
    showBackground = true,
    heightDp = 320,
    widthDp = 240,
)
@Composable
fun ImageListPreview() {
    KtComposeLayoutsExampleTheme {
        ImageList()
    }
}

@Preview(
    showBackground = true,
    heightDp = 320,
    widthDp = 240,
    // device = Devices.NEXUS_5
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
    widthDp = 240,
    // device = Devices.NEXUS_5
)
@Composable
fun LazyListPreview() {
    KtComposeLayoutsExampleTheme {
        LazyList()
    }
}
