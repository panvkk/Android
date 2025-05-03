package com.example.pixelsorting

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.compose.ui.platform.LocalContext
import com.example.pixelsorting.data.AppContainer
import com.example.pixelsorting.data.DefaultAppContainer

class PixelSortingApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }

    fun shouldRequestNotificationPermission(): Boolean {
        val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return prefs.getBoolean("need_permission_request", true)
    }

    fun setPermissionRequested() {
        val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("need_permission_request", false).apply()
    }
}