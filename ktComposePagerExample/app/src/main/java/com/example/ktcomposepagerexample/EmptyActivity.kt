package com.example.ktcomposepagerexample

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

class EmptyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // https://stackoverflow.com/questions/69688138/how-to-hide-navigationbar-and-statusbar-in-jetpack-compose
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        // device art generator
        // https://developer.android.com/distribute/marketing-tools/device-art-generator

        setContent {
            contentUI()
        }
    }
}


@Composable
fun contentUI(modifier: Modifier = Modifier
) {
    // accompanist code
//    val systemUiController: SystemUiController = rememberSystemUiController()
//
//    systemUiController.isStatusBarVisible = false // Status bar
//    systemUiController.isNavigationBarVisible = false // Navigation bar
//    systemUiController.isSystemBarsVisible = false // Status & Navigation bars

    Surface(modifier = modifier.fillMaxSize()) {
        // Text("test")
    }

}

@Preview(
    device = Devices.NEXUS_5X,
    showSystemUi = false,
    showBackground = true,
    backgroundColor = 0xffffff
)
@Composable
fun contentUIPreview() {
    contentUI()
}