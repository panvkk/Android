package com.example.pixelsorting.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.work.WorkInfo
import com.example.pixelsorting.KEY_IMAGE_URI
import com.example.pixelsorting.KEY_SORT_KEY
import com.example.pixelsorting.PixelSortingApplication
import com.example.pixelsorting.data.PSRepo
import com.example.pixelsorting.model.PixelSortingSettings
import com.example.pixelsorting.model.SortKey
import com.example.pixelsorting.model.SortType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

sealed interface SortingUiState {
    object Loading : SortingUiState
    object Default : SortingUiState
    data class Complete(val outputUri: String) : SortingUiState
}

class AppViewModel(private val pixelSortingRepo: PSRepo) : ViewModel() {

    val sortingUiState: StateFlow<SortingUiState> = pixelSortingRepo.outputWorkInfo
        .map { info ->
            val outputImageUri = info.outputData.getString(KEY_IMAGE_URI)
            when {
                info.state.isFinished && !outputImageUri.isNullOrEmpty() -> {
                    SortingUiState.Complete(outputImageUri)
                }
                info.state == WorkInfo.State.CANCELLED -> {
                    SortingUiState.Default
                }
                else -> SortingUiState.Loading
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SortingUiState.Default
        )

    private val _settings = MutableStateFlow(
        PixelSortingSettings(
            imageUri = "",
            sortType = SortType.COLUMNS,
            sortKey = SortKey.BRIGHTNESS,
            effectLevel = 50
        )
    )
    val settings: StateFlow<PixelSortingSettings> = _settings

    fun updateSettings(
        imageUri: String,
        effectLevel: Int,
        sortKey: SortKey,
        sortType: SortType,
    ) {
        _settings.update {
            PixelSortingSettings(
                imageUri = imageUri,
                effectLevel = effectLevel,
                sortKey = sortKey,
                sortType = sortType
            )
        }
    }

    fun applySorting() {
        pixelSortingRepo.applyWork(settings.value)
    }

    fun cancelSorting() {
        pixelSortingRepo.cancelWork()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val pixelSortingRepo =
                    (this[APPLICATION_KEY] as PixelSortingApplication).container.pixelSortingRepo
                AppViewModel(
                    pixelSortingRepo
                )
            }
        }
    }
}