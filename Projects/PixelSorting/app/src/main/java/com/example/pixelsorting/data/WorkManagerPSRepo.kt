package com.example.pixelsorting.data

import android.content.Context
import android.net.Uri
import androidx.lifecycle.asFlow
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.pixelsorting.IMAGE_MANIPULATION_WORK_NAME
import com.example.pixelsorting.KEY_IMAGE_URI
import com.example.pixelsorting.KEY_INPUT_SETTINGS
import com.example.pixelsorting.TAG_OUTPUT
import com.example.pixelsorting.model.PixelSortingSettings
import com.example.pixelsorting.workers.CleanupWorker
import com.example.pixelsorting.workers.SaveImageToFileWorker
import com.example.pixelsorting.workers.SortingWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.serialization.json.Json

class WorkManagerPSRepo(context: Context) : PSRepo {
    private val workManager = WorkManager.getInstance(context)

    override val outputWorkInfo: Flow<WorkInfo> =
        workManager.getWorkInfosByTagLiveData(TAG_OUTPUT).asFlow().mapNotNull {
            if(it.isNotEmpty()) it.first() else null
        }

    override fun applyWork(settings: PixelSortingSettings) {
        var continuation = workManager.beginUniqueWork(
            IMAGE_MANIPULATION_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            OneTimeWorkRequest.from(CleanupWorker::class.java)
        )

        val pixelSortingBuilder = OneTimeWorkRequestBuilder<SortingWorker>()
            .addTag(TAG_OUTPUT)
        pixelSortingBuilder.setInputData(createDataForApplyWorkRequest(settings))

        continuation = continuation.then(pixelSortingBuilder.build())

        continuation.enqueue()
    }

    override fun cancelWork() {
        workManager.cancelUniqueWork(IMAGE_MANIPULATION_WORK_NAME)
    }

    override fun saveWork(imageUri: String) {
        val save = OneTimeWorkRequestBuilder<SaveImageToFileWorker>()
            .setInputData(createDataForSaveWorkRequest(imageUri))
            .build()

        workManager.enqueue(save)
    }

    private fun createDataForApplyWorkRequest(settings: PixelSortingSettings) : Data {
        val builder = Data.Builder()
        builder.putString(KEY_INPUT_SETTINGS, Json.encodeToString(settings))
        return builder.build()
    }

    private fun createDataForSaveWorkRequest(imageUri: String) : Data {
        val builder = Data.Builder()
        builder.putString(KEY_IMAGE_URI, imageUri)
        return builder.build()
    }
}