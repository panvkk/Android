package com.example.flightsearch.ui

import android.database.sqlite.SQLiteException
import android.util.Log
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
import com.example.flightsearch.data.airports.Airport
import com.example.flightsearch.data.airports.AirportDao
import com.example.flightsearch.data.favourites.Favourite
import com.example.flightsearch.data.favourites.FavouritesRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface SearchUiState {
    data class Input(val suggestionList: List<Airport>) : SearchUiState
    data class Query(val queryList: List<Flight>, val airport: Airport) : SearchUiState
    data class Empty(val favouriteList: List<Favourite>) : SearchUiState
    object Loading : SearchUiState
}

class FlightSearchViewModel(
    private val airportDao: AirportDao,
    private val favouritesRepository: FavouritesRepository
): ViewModel() {
    var searchUiState: SearchUiState by mutableStateOf(SearchUiState.Loading)
        private set

    private var _userInput = MutableStateFlow("")
    var userInput: StateFlow<String> = _userInput

    init {
        viewModelScope.launch {
            updateEmpty()
        }
    }

    /**
     *  Logic for Favourites database
     */

    fun addFavourite(flight: Flight) {
        viewModelScope.launch {
            val favourite =
                Favourite(
                    departCode = flight.depart.iataCode,
                    arriveCode = flight.arrive.iataCode
                )
            favouritesRepository.insert(favourite)
        }
    }

    fun deleteFavourite(flight: Flight) {
        viewModelScope.launch {
            favouritesRepository.deleteByAirports(
                departCode = flight.depart.iataCode,
                arriveCode = flight.arrive.iataCode
            )
        }
    }

    /**
     * Updating State
     */

    fun updateUserInput(currentInput: String) {
        _userInput.update {
            currentInput
        }
    }

    fun updateEmpty() {
        viewModelScope.launch {
            searchUiState = SearchUiState.Empty(
                favouriteList =
                    toFlightsConversion(
                        favouritesRepository.getAllFavouritesStream()
                            .stateIn(
                                scope = viewModelScope,
                                started = SharingStarted.WhileSubscribed(5000),
                                initialValue = emptyList()
                            )
                    )

            )
        }
    }


    fun updateQuery(airport: Airport) {
        viewModelScope.launch {
            searchUiState = SearchUiState.Query(
                queryList = createFlightsList(airport),
                airport = airport
            )
        }
        updateUserInput(airport.iataCode)
    }

    fun updateSuggestionList(input: String) {
        viewModelScope.launch {
            searchUiState = SearchUiState.Input(airportDao.getAirportSuggestions(input))
        }
    }

    /**
     * Creating Lists
     */

    private suspend fun toFlightsConversion(favourites: List<Favourite>) : List<Flight> {
        val flights: MutableList<Flight> = mutableListOf()
        favourites.forEach{
            val flight = Flight(
                depart = airportDao.getByIata(it.departCode),
                arrive = airportDao.getByIata(it.arriveCode),
                favourite = true
            )
            flights.add(flight)
        }
        return flights
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
                    depart = airport,
                    favourite =
                        favouritesRepository
                            .getByAirports(
                                departCode = airport.iataCode, arriveCode = it.iataCode
                            ).isNotEmpty()
                )
            result.add(flight)
        }
        return result
    }

    private suspend fun getAllAirports() : List<Airport> {
        return airportDao.getAllAirports()
    }

    /**
     * Dependency Injection for ViewModel
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FlightSearchApplication)
                FlightSearchViewModel(
                    application.container.database.airportDao(),
                    application.container.favouritesRepository
                )
            }
        }
    }
}