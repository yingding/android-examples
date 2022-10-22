package com.example.ktcomposepagerexample.main

import androidx.annotation.DrawableRes
import com.example.ktcomposepagerexample.R

data class Page(val title: String, val description: String, @DrawableRes val image: Int)

val onboardPages = listOf(
    Page(
        "Welcome to the demo",
        "This demo onboarding app shows how to make an onboarding using jetpack compose with accompanist lib.",
         R.drawable.ic_baseline_accessibility_24
    ),
    Page(
        "Lorem Ipsum",
        "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...",
        R.drawable.ic_baseline_accessibility_24
    ),
    Page(
        "Privacy",
        "This example will not share your data with any third party. No internet connection required!",
        R.drawable.ic_baseline_accessibility_24
    )
)

