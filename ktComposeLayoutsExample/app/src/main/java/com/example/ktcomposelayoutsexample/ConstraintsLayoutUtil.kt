package com.example.ktcomposelayoutsexample

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.layoutId
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
         * ConstraintLayout's size will be as small as possible to wrap its content.
         * That's why it seems Text is centered around the Button instead of the parent.
         * If other sizing behavior is desired, sizing modifiers (e.g. fillMaxSize, size)
         * should be applied to the ConstraintLayout composable as with any other layout in Compose
         */
    }
}

// Example of Compose DSL for constraint layout helpers such as guidelines, barriers and chains
@Composable
fun ConstraintLayoutHelperContent() {
    ConstraintLayout {
        // Creates references for the three composables
        // in the ConstraintLayout's body
        val (button1, button2, text) = createRefs()

        Button(
            onClick = { /* Do nothing */ },
            modifier = Modifier.constrainAs(button1) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text("Button 1")
        }

        Text("Text", modifier = Modifier.constrainAs(text) {
            top.linkTo(button1.bottom, margin = 16.dp)
            centerAround(button1.end)
        })

        // create a end barrier for button1 and text, since text is center around the end of button1
        // this barrier will be the end of text
        val barrier = createEndBarrier(button1, text)

        Button(
            onClick = { /* Do nothing */ },
            modifier = Modifier.constrainAs(button2) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(barrier)
            }
        ) {
            Text("Button 2")
        }

        /* barriers (and all the other helpers) can be created in the body of ConstraintLayout,
         * but not inside constrainAs.
         *
         * linkTo can be used to constrain with guidelines and barriers the same way it works
         * for edges of layouts.
         */
    }
}

/**
 * Customizing dimensions
 * By default, the children of ConstraintLayout will be allowed to choose the size they need to wrap
 * their content, this means that a Text is able to go outside the screen bounds when the text is
 * too long:
 */
@Composable
fun LargeConstraintLayout() {
    ConstraintLayout {
        val text = createRef()

        // Define the guideline in the middle of the screen width
        val guideline = createGuidelineFromStart(fraction = 0.5f)
        Text(
            "This is a very very very very very very very long text",
            modifier = Modifier.constrainAs(text) {
                linkTo(start = guideline, end = parent.end)
                // use Dimension to wrap content, to show all text on the screen
                width = Dimension.preferredWrapContent
                /* Dimension behavior are
                 *
                 * preferredWrapContent: the layout is wrap content, subject to the constraints in that dimension.
                 * wrapContent: the layout is wrap content even if the constraints would not allow it
                 * fillToConstraints: the layout will expand to fill the space defined by its constraints in that dimension
                 * preferredValue: the layout is a fixed dp value, subject to the constraints in that dimension
                 * value: the layout is a fixed dp value, regardless of the constraints in that dimension
                 */

                // Dimension can be coerced:
                // width = Dimension.preferredWrapContent.atLeast(100.dp)
            }
        )
    }
}

/**
 * Decouple Constrains from ConstraintLayout
 */
@Composable
fun DecoupleConstraintLayout() {
    BoxWithConstraints {
        val constraints = if (maxWidth < maxHeight) {
            decoupledConstraints(margin = 16.dp) // Portrait constraints
        } else {
            decoupledConstraints(margin = 32.dp) // Landscape constraints
        }

        ConstraintLayout(constraints) {
            Button(
                onClick = {/* Do nothing */},
                // what is tag string used for
                modifier = Modifier.layoutId("button", "decouple_button")
            ) {
                Text("Button")
            }
            Text("Text", modifier = Modifier.layoutId("text", "decouple_text"))
        }

    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor("button")
        val text = createRefFor("text")

        constrain(button) {
            top.linkTo(parent.top, margin = margin)
        }
        constrain(text) {
            top.linkTo(button.bottom, margin = margin)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ConstraintLayoutContentPreview() {
    KtComposeLayoutsExampleTheme {
        ConstraintLayoutContent()
    }
}

@Preview(showBackground = true)
@Composable
fun ConstraintLayoutHelperContentPreview() {
    KtComposeLayoutsExampleTheme {
        ConstraintLayoutHelperContent()
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun LargeConstraintLayoutPreview() {
    KtComposeLayoutsExampleTheme {
        LargeConstraintLayout()
    }
}

@Preview(showBackground = true, widthDp = 240, heightDp = 320,
    name = "Portrait_DecoupleConstraintLayoutPreview")
@Preview(showBackground = true, widthDp = 320, heightDp = 240,
    name = "Landscape_DecoupleConstraintLayoutPreview")
@Composable
fun DecoupleConstraintLayoutPreview() {
    KtComposeLayoutsExampleTheme {
        DecoupleConstraintLayout()
    }
}