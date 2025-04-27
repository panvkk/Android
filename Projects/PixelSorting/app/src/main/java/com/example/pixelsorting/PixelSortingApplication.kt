package com.example.pixelsorting

import android.app.Application
import androidx.compose.ui.platform.LocalContext
import com.example.pixelsorting.data.AppContainer
import com.example.pixelsorting.data.DefaultAppContainer

class PixelSortingApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}