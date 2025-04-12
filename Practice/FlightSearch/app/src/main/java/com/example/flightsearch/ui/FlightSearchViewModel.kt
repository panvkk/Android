package com.example.flightsearch.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.FlightSearchApplication
import com.example.flightsearch.data.airports.AirportDao
import com.example.flightsearch.data.favourites.FavouritesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

sealed interface SearchUiState {
    data class Input(val suggestions: List<Airport>)
    data class Query(val userQuery: String)
    object Empty
}

data class FlightsListState(
    val Flights: List<Flight>?
)


class FlightSearchViewModel(
    val airportDao: AirportDao
): ViewModel() {
    var searchUiState by mutableStateOf(SearchUiState.Empty)
        private set

    private var _userInput = MutableStateFlow("")
    var userInput: StateFlow<String> = _userInput

//    fun updateSuggestionList(input: String) {
//        viewModelScope.launch {
//            searchUiState = SearchUiState.Input(
//                suggestions = airportDao.getAirportSuggestions(input).toList()
//            )
//        }
//    }


    /**
     * Dependency Injection for ViewModel
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FlightSearchApplication)
                val flightSearchRepository = application.container.favouritesRepository
                FlightSearchViewModel(application.container.database.airportDao())
            }
        }
    }
}