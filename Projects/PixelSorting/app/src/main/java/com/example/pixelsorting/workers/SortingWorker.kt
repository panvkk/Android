package com.example.pixelsorting.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.pixelsorting.KEY_INPUT_SETTINGS
import com.example.pixelsorting.model.PixelSortingSettings

private const val TAG = "SortingWorker"

class SortingWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        try {
            val inputSettings: PixelSortingSettings = inputData.keyValueMap[KEY_INPUT_SETTINGS]

            val resourceUri = inputSettings.
        }
    }
}