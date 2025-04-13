package com.example.flightsearch

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.ui.FlightSearchViewModel
import com.example.flightsearch.ui.HomeScreen

@Composable
fun FlightSearchApp() {
    val viewModel: FlightSearchViewModel = viewModel(factory = FlightSearchViewModel.Factory)

    HomeScreen(
        viewModel = viewModel,
        searchUiState = viewModel.searchUiState
    )
}