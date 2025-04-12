package com.example.flightsearch.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed interface SearchUiState {
    data class Input(val suggestions: List<Airport>)
    data class Query(val userQuery: String)
    object Empty
}

data class FlightsListState(
    val Flights: List<Flight>?
)


class FlightSearchViewModel: ViewModel() {
    var searchUiState by mutableStateOf(SearchUiState.Empty)
        private set

    private var _userInput = MutableStateFlow("")
    var userInput: StateFlow<String> = _userInput
}