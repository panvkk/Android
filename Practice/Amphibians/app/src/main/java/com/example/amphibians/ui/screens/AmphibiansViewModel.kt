package com.example.amphibians.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.example.amphibians.model.AmphibianInfo

sealed interface AmphibiansUiState {
    data class Success(val info: List<AmphibianInfo>) : AmphibiansUiState
    object Error: AmphibiansUiState
    object Loading: AmphibiansUiState
}

class AmphibiansViewModel {

    val amphibiansUiState: AmphibiansUiState by mutableStateOf(AmphibiansUiState.Loading)

    init {

    }

    fun getAmphibiansInfo() {

    }
}