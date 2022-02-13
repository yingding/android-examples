package com.example.ktcomposelayoutsexample

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ktcomposelayoutsexample.ui.theme.KtComposeLayoutsExampleTheme

//class CustomLayoutsUtil {
//}

// functional extension for the Modifier
@Composable
fun Modifier.firstBaselineTopTop(firstBaselineToTop: Dp) = this.then(
    // Modifier.layout function
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)

        // Check the composable has a first baseline
        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseline = placeable[FirstBaseline]

        // Hieght of the composable with padding - first baseline
        val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
        val height =placeable.height + placeableY

        // Define a Layout width and Height
        layout(placeable.width, height) {
            // When ethe composable gets placed on top left corner.
            placeable.placeRelative(0, placeableY)
        }
    }
)

@Composable
fun MyOwnColumn(
    modifier: Modifier = Modifier,
    // custom layout attributes
    content: @Composable () -> Unit
) {
    // https://www.youtube.com/watch?v=zMKMwh9gZuI
    // Layout composable
    Layout(
        content = content,
        modifier = modifier,
    ) { measurables: List<Measurable>,
        constraints: Constraints ->
        // Measure children
        val placeables = measurables.map { measurable ->
            // Measure each child
            measurable.measure(constraints)
        }

        // Set the size of the layout as big as it can
        layout(constraints.maxWidth, constraints.maxHeight) {
            // Place children
            // Track the y co-ord we have placed children up to
            var yPosition = 0

            placeables.forEach { placeable ->
                // Position item on the sreen
                placeable.placeRelative(x = 0, y = yPosition)
                // Record the y co-ord placed up
                yPosition += placeable.height
            }
        }
    }
}

@Composable
fun BodyContentCustom(modifier: Modifier = Modifier) {
    MyOwnColumn(modifier.padding(8.dp)) {
        Text("MyOwnColumn")
        Text("places items")
        Text("vertically.")
        Text("We've done it by hand!")
    }
}

@Preview(
    showBackground = true
)
@Composable
fun TextWithPaddingToBaselinePreview() {
    KtComposeLayoutsExampleTheme {
        Text("Hi there!", Modifier.firstBaselineTopTop(32.dp))
    }
}

@Preview(
    showBackground = true
)
@Composable
fun TextWithNormalPaddingPreview() {
    KtComposeLayoutsExampleTheme {
        Text("Hi there!", Modifier.padding(top = 32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun BodyContentCustomPreview() {
    KtComposeLayoutsExampleTheme {
        BodyContentCustom()
    }
}

