package com.example.flightsearch.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.FlightSearchApplication
import com.example.flightsearch.data.airports.Airport
import com.example.flightsearch.data.airports.AirportDao
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface SearchUiState {
    data class Input(val suggestions: List<Airport>) : SearchUiState
    data class Query(val queryList: List<Flight>) : SearchUiState
    object Empty : SearchUiState
}

class FlightSearchViewModel(
    private val airportDao: AirportDao
): ViewModel() {
    var searchUiState: SearchUiState by mutableStateOf(SearchUiState.Empty)
        private set

    private var _userInput = MutableStateFlow("")
    var userInput: StateFlow<String> = _userInput

    fun updateInput(currentInput: String) {
        _userInput.update {
            currentInput
        }
    }

    fun updateQuery(airport: Airport) {
        viewModelScope.launch {
            searchUiState = SearchUiState.Query(
                queryList = createFlightsList(airport)
            )
        }
        updateInput(airport.iataCode)
    }

    fun updateSuggestionList(input: String) {
        viewModelScope.launch {
            searchUiState =
                if(input != "") {
                    SearchUiState.Input(airportDao.getAirportSuggestions(input))
                } else {
                    SearchUiState.Empty
                }
        }
    }

    private suspend fun getAllAirports() : List<Airport> {
        return airportDao.getAllAirports()
    }

    private suspend fun createFlightsList(airport: Airport) : List<Flight> {
        val result: MutableList<Flight> = mutableListOf()
        val airports = getAllAirports()
        airports.forEach {
            if(airport == it) {
                return@forEach
            }
            val flight =
                Flight(
                    arrive = it,
                    depart = airport
                )
            result.add(flight)
        }
        return result
    }

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