package com.example.ktcomposelayoutsexample

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ktcomposelayoutsexample.ui.theme.KtComposeLayoutsExampleTheme

//class CustomLayoutsUtil {
//}

// functional extension for the Modifier
@Composable
fun Modifier.firstBaselineTopTop(firstBaselineToTop: Dp) = this.then(
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


