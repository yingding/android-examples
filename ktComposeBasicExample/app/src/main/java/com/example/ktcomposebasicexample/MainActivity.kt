package com.example.ktcomposebasicexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ktcomposebasicexample.ui.theme.KtComposeBasicExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KtComposeBasicExampleTheme {
                MyApp()
            }
        }
    }
}

@Composable
private fun MyApp(names: List<String> = listOf("World", "Compose")) {
    // A surface container using the 'background' color from the theme
//    Surface(color = MaterialTheme.colors.background) {
//        Greeting("Android")
//    }
    val modifier = Modifier.padding(vertical = 4.dp)

    Column (modifier = modifier){
        for (name in names) {
            Greeting(name = name)
        }
    }
}

@Composable
fun Greeting(name: String) {
    // var expanded = remember { mutableStateOf(false) }
    // By delegation need
    // import androidx.compose.runtime.getValue
    // import androidx.compose.runtime.setValue
    var expanded by remember { mutableStateOf(false) }

    // No need to remember, it is a simple calculation
    val extraPadding = if (expanded) 48.dp else 0.dp

    // Modifier of a composable is regarding to the parent
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(
            vertical = 4.dp,
            horizontal = 8.dp
        )

    ) {
        // Modifier tells the padding for surface
        Row(Modifier.padding(24.dp)) {
            // Modifier tells the padding in Row
            Column(modifier = Modifier
                .weight(1f)
                // adding extra padding to the parent row
                .padding(bottom = extraPadding)
            ) {
                Text(text = "Hello,")
                Text(text = name)
            }
            OutlinedButton(
                onClick = { expanded = !expanded}
            ) {
                Text(
                    if (expanded) "Show less" else "Show more")
            }
        }
    }
}

@Preview(
    showBackground = true,
    name = "Text preview",
    widthDp = 320
)
@Composable
fun DefaultPreview() {
    KtComposeBasicExampleTheme {
        MyApp()
    }
}