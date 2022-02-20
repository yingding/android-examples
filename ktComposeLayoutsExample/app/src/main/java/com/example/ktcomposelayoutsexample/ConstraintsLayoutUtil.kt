package com.example.ktcomposelayoutsexample

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.ktcomposelayoutsexample.ui.theme.KtComposeLayoutsExampleTheme

@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout {
        // Create references for the composables to constrain
        // so that the element can have reference to each other
        val (button, text) = createRefs()

        Button(
            onClick = { /* Do something */ },
            // Assign references "button" to the Button composable
            // and constrain ti to the top of the ConstraintLayout
            modifier = Modifier.constrainAs(button) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            // content of the button
            Text("Button")
        }

        // Assign reference "text" to the Text composable
        // and constrain it to the bottom of the Button composable
        Text("Text", Modifier.constrainAs(text) {
            top.linkTo(button.bottom, margin = 16.dp)
            // Centers Text horizontally in the ConstraintLayout
            // sets both the start and end to the edges of parent
            centerHorizontallyTo(parent)
        })

        /*
         ConstraintLayout's size will be as small as possible to wrap its content.
         That's why it seems Text is centered around the Button instead of the parent.
         If other sizing behavior is desired, sizing modifiers (e.g. fillMaxSize, size)
         should be applied to the ConstraintLayout composable as with any other layout in Compose
         */
    }
}

@Preview(showBackground = true)
@Composable
fun ConstraintLayoutContentPreview() {
    KtComposeLayoutsExampleTheme {
        ConstraintLayoutContent()
    }
}
