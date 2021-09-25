package com.example.quadflask

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        // https://medium.com/androiddevelopers/appcompat-v23-2-daynight-d10f90c83e94
        AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_YES
        )
    }
}