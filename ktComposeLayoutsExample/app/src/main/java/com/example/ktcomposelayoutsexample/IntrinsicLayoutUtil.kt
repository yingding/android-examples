package com.example.ktcomposelayoutsexample

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ktcomposelayoutsexample.ui.theme.KtComposeLayoutsExampleTheme

/**
 * One of the rules of Compose is that you should only measure your children once,
 * measuring children twice throws a runtime exception. However, there are times
 * when you need some information about your children before measuring them.
 *
 * Intrinsic lets you query children before they are actually measured.
 */


@Composable
fun TwoTexts(modifier: Modifier = Modifier, text1: String, text2: String) {
    Row(modifier = modifier) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
                .wrapContentWidth(Alignment.Start),
            text = text1
        )

        Divider(color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))

        Text(
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
                .wrapContentWidth(Alignment.End),
            text = text2
        )
    }
}

@Preview(heightDp = 320)
@Composable
fun TwoTextsPreview() {
    KtComposeLayoutsExampleTheme {
        Surface {
            TwoTexts(text1 = "Hi", text2 = "there")
        }
    }
}