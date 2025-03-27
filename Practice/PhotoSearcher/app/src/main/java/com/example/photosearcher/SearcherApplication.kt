package com.example.photosearcher

import android.app.Application
import com.example.photosearcher.data.AppContainer
import com.example.photosearcher.data.DefaultAppContainer

class SearcherApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}