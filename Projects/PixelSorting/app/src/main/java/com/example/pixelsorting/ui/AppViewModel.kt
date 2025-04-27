package com.example.pixelsorting.ui

import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pixelsorting.PixelSortingApplication
import com.example.pixelsorting.data.PSRepo
import com.example.pixelsorting.model.PixelSortingSettings
import com.example.pixelsorting.model.SortKey
import com.example.pixelsorting.model.SortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

class AppViewModel(private val pixelSortingRepo: PSRepo) : ViewModel() {

    private val _currentImageUri = MutableStateFlow(null)
    val currentImageUri: StateFlow<Uri?> = _currentImageUri

    fun applySorting(settings: PixelSortingSettings) {
        pixelSortingRepo.applyWork(settings)
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