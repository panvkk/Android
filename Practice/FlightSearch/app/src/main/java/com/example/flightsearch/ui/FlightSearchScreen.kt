package com.example.flightsearch.ui

import android.graphics.Paint.Align
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsearch.R
import com.example.flightsearch.data.airports.Airport

data class Flight(
    val depart: Airport,
    val arrive: Airport,
    val featured: Boolean
)


@Composable
fun HomeScreen(
    viewModel: FlightSearchViewModel,
    searchUiState: SearchUiState
) {
    val currentInput by viewModel.userInput.collectAsState()

    Scaffold(
        topBar =  {
            AppTopBar(
                modifier = Modifier.systemBarsPadding(),
                userInput = currentInput,
                onValueChange = {
                    viewModel.updateInput(it)
                    viewModel.updateSuggestionList(it)
                }
            )
        }
    ) {
        when(searchUiState) {
            is SearchUiState.Input ->
                AirportsList(
                    airports = searchUiState.suggestions,
                    viewModel = viewModel,
                    modifier = Modifier.padding(it).fillMaxSize()
                )
            is SearchUiState.Query ->
                FlightsList(
                    flights = searchUiState.queryList,
                    pageTitle = stringResource(R.string.query_list),
                    modifier = Modifier.padding(it).fillMaxSize()
                )
            is SearchUiState.Empty ->
                FlightsList(
                    flights = emptyList(),
                    pageTitle = stringResource(R.string.favourites_list),
                    modifier = Modifier.padding(it).fillMaxSize()
                )
        }
    }
}

@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    userInput: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(stringResource(R.string.app_name))
        EditQueryField(
            value = userInput,
            onValueChange = onValueChange
        )
    }
}

@Composable
fun EditQueryField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    Card(
        elevation = CardDefaults.elevatedCardElevation(12.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text("Enter your query") },
            singleLine = true
        )
    }
}

@Composable
fun AirportsList(
    airports: List<Airport>,
    viewModel: FlightSearchViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier) {
        items(airports) { airport ->
            AirportRow(
                airport,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.medium_padding))
                    .clickable {
                        viewModel.updateQuery(airport)
                    }
            )
        }
    }
}

@Composable
fun AirportRow(
    airport: Airport,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        Text(
            airport.iataCode,
            modifier = Modifier.padding(end = dimensionResource(R.dimen.small_padding))
        )
        Text(
            airport.name
        )
    }
}

@Composable
fun FlightsList(
    flights: List<Flight>,
    pageTitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(pageTitle)
        LazyColumn {
            items(flights) { flight ->
                FlightCard(
                    flight,
                    onClickBehavior = { },
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.medium_padding))
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun FlightCard(
    flight: Flight,
    onClickBehavior: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier,
        elevation = CardDefaults.elevatedCardElevation(dimensionResource(R.dimen.default_card_elevation))
    ) {
        Row {
            Column {
                Text(
                    stringResource(R.string.depart),
                    modifier = Modifier.padding(dimensionResource(R.dimen.small_padding))
                )
                Row(
                    modifier = Modifier.padding(dimensionResource(R.dimen.small_padding))
                ) {
                    Text(
                        flight.depart.iataCode,
                        modifier = Modifier.padding(end = dimensionResource(R.dimen.small_padding))
                    )
                    Text(flight.depart.name)
                }
                Text(
                    stringResource(R.string.arrive),
                    modifier = Modifier.padding(dimensionResource(R.dimen.small_padding))
                )
                Row(
                    modifier = Modifier.padding(dimensionResource(R.dimen.small_padding))
                ) {
                    Text(
                        flight.arrive.iataCode,
                        modifier = Modifier.padding(end = dimensionResource(R.dimen.small_padding))
                    )
                    Text(flight.arrive.name)
                }
            }
            Spacer(Modifier.weight(1f))
            Icon(
                painter = painterResource(R.drawable.favourite),
                contentDescription = stringResource(R.string.favourite),
                modifier = Modifier
                    .size(dimensionResource(R.dimen.icon_size))
                    .align(Alignment.CenterVertically)
                    .padding(dimensionResource(R.dimen.large_padding))
                    .clickable {

                    }
            )
        }
    }
}

@Preview
@Composable
fun FlightCardPreview() {
    val mock = Flight(
        depart = Airport(
            iataCode = "SVO",
            name = "Sheremet'evo Airport",
            id = 1,
            passengers = 100
        ),
        arrive = Airport(
            iataCode = "LAX",
            name = "Los Angeles Airport",
            id = 1,
            passengers = 100
        ),
        featured = true
    )
    FlightCard(mock, onClickBehavior = {} )
}

@Preview
@Composable
fun FlightsListPreview() {
    val mock = Flight(
        depart = Airport(
            iataCode = "SVO",
            name = "Sheremet'evo Airport",
            id = 1,
            passengers = 100
        ),
        arrive = Airport(
            iataCode = "LAX",
            name = "Los Angeles Airport",
            id = 1,
            passengers = 100
        ),
        featured = true
    )
    FlightsList(listOf(mock, mock, mock, mock, mock), "Mock Flights lol")
}