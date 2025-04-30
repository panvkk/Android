package com.example.pixelsorting.workers

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.net.toUri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.pixelsorting.KEY_IMAGE_URI
import com.example.pixelsorting.KEY_INPUT_SETTINGS
import com.example.pixelsorting.model.PixelSortingSettings
import com.example.pixelsorting.model.SortKey
import com.example.pixelsorting.model.SortType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

private const val TAG = "SortingWorker"

class SortingWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {

        val inputJson = inputData.getString(KEY_INPUT_SETTINGS)

        var imageUri = ""
        var sortKey = SortKey.HUE
        var sortType = SortType.COLUMNS
        var effectLevel = 1

        if(inputJson != null) {
            val settings: PixelSortingSettings = Json.decodeFromString(inputJson)

            imageUri = settings.imageUri
            sortKey = settings.sortKey
            sortType = settings.sortType
            effectLevel = settings.effectLevel
        }

        return withContext(Dispatchers.IO) {
            return@withContext try {
                require(imageUri.isNotBlank()) {
                    val errorMessage =
                        "Invalid input uri"
                    Log.e(TAG, errorMessage)
                    Result.failure()
                }

                val resolver = applicationContext.contentResolver

                val picture = BitmapFactory.decodeStream(
                    resolver.openInputStream(imageUri.toUri())
                )

                val output =
                    pixelSort(
                        source = picture,
                        sortType = sortType,
                        sortKey = sortKey,
                        effectLevel = effectLevel
                    )

                val outputUri = writeBitmapToFile(applicationContext, output)

                val outputData = workDataOf(KEY_IMAGE_URI to outputUri.toString())

                Result.success(outputData)
            } catch(throwable: Throwable) {
                Log.e(
                    TAG,
                    "Error sorting pixels on image.",
                    throwable
                )
                Result.failure()
            }
        }
    }
}