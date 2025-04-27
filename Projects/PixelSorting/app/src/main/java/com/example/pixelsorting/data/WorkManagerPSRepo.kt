package com.example.pixelsorting.data

import android.content.Context
import android.net.Uri
import androidx.work.Data
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.pixelsorting.model.PixelSortingSettings
import kotlinx.coroutines.flow.Flow

class WorkManagerPSRepo(val context: Context) : PSRepo {
    private val workManager = WorkManager.getInstance(context)

    override val outputWorkInfo: Flow<WorkInfo>
        get() = TODO("Not yet implemented")

    override fun applyWork(settings: PixelSortingSettings) {
        TODO("Not yet implemented")
    }

    override fun cancelWork() {
        TODO("Not yet implemented")
    }

    private fun createDataForWorkRequest(settings: PixelSortingSettings) : Data {
        val builder: Data.Builder()
        builder.put
    }
}