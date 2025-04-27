package com.example.pixelsorting.data

import kotlinx.coroutines.flow.Flow
import androidx.work.WorkInfo
import com.example.pixelsorting.model.PixelSortingSettings


interface PSRepo {
    val outputWorkInfo: Flow<WorkInfo>
    fun applyWork(settings: PixelSortingSettings)
    fun cancelWork()
}