package com.example.ktcomposebasicexample

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
private fun MyApp() {
    // A surface container using the 'background' color from the theme
//    Surface(color = MaterialTheme.colors.background) {
//        Greeting("Android")
//    }

    // var shouldShowOnboarding by remember { mutableStateOf(true) }
    // remember the state for configuration change
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    if (shouldShowOnboarding) {
        // pass a callback function down to modified the hoisted state
        OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false})
    } else {
        Greetings()
    }
}

//@Composable
//fun Greetings(names: List<String> = listOf("World", "Compose")) {
//    val modifier = Modifier.padding(vertical = 4.dp)
//    Column (modifier = modifier){
//        for (name in names) {
//            Greeting(name = name)
//        }
//    }
//}

/**
 * Using LazyColumn, which is equivalent to RecyclerView List
 */
@Composable
fun Greetings(names: List<String> = List(1000) {"$it"}) {
    val modifier = Modifier.padding(vertical = 4.dp)
    LazyColumn (modifier = modifier){
        // androidx.compose.foundation.lazy.items
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

@Composable
fun Greeting1(name: String) {
    // var expanded = remember { mutableStateOf(false) }
    // By delegation need
    // import androidx.compose.runtime.getValue
    // import androidx.compose.runtime.setValue
    var expanded by remember { mutableStateOf(false) }

    // No need to remember, it is a simple calculation
    // val extraPadding = if (expanded) 48.dp else 0.dp
    val extraPadding by animateDpAsState(
        if (expanded) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

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
                // adding extra padding to the parent row and padding can not be negative.
                .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                Text(text = "Hello,")
                //Text(text = name, style = MaterialTheme.typography.h4)
                Text(text = name, style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.ExtraBold
                ))
            }
            OutlinedButton(
                onClick = { expanded = !expanded}
            ) {
                Text(
                    if (expanded) stringResource(R.string.show_less) else stringResource(R.string.show_more)
                                    )
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(name)
    }
}

@Composable
fun CardContent(name: String) {
    var expanded by remember { mutableStateOf(false) }

    // Modifier tells the padding for surface
    Row( modifier = Modifier
        .padding(12.dp)
        .animateContentSize(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    ) {
        // Modifier tells the padding in Row
        Column(modifier = Modifier
            .weight(1f)
            // adding extra padding to the parent row and padding can not be negative.
            .padding(12.dp)
        ) {
            Text(text = "Hello,")
            //Text(text = name, style = MaterialTheme.typography.h4)
            Text(text = name, style = MaterialTheme.typography.h4.copy(
                fontWeight = FontWeight.ExtraBold
            ))
            if (expanded) {
                Text(
                    text = ("Composem ipsum color sit lazy, " +
                            "padding theme elit, sed do bouncy. ").repeat(4)
                )
            }
        }
        IconButton(onClick = {expanded = !expanded}) {
            Icon(
                imageVector = if (expanded) Filled.ExpandLess else Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }
            )
        }
    }

}


@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit) {
    // TODO: this state should be hoisted
    // var shouldShowOnboarding by remember { mutableStateOf(true) }

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text("Welcome to the Basics Codlab!")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onContinueClicked
                // onClick = { shouldShowOnboarding = false}
            ) {
                Text("continue")
            }
        }
    }
}


@Preview(
    showBackground = true,
    name = "DefaultPreviewDark",
    uiMode = UI_MODE_NIGHT_YES,
    widthDp = 320
)
@Preview(
    showBackground = true,
    name = "DefaultPreviewLight",
    widthDp = 320
)
@Composable
fun DefaultPreview() {
    KtComposeBasicExampleTheme {
        Greetings()
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    KtComposeBasicExampleTheme {
        OnboardingScreen(onContinueClicked = {}) // Don nothing on click
    }
}