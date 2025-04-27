package com.example.pixelsorting.data

import android.content.Context

interface AppContainer {
    val pixelSortingRepo: PSRepo
}

class DefaultAppContainer(context: Context) : AppContainer {
    override val pixelSortingRepo = WorkManagerPSRepo(context)
}