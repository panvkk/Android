package com.example.flightsearch.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.R
import com.example.flightsearch.data.airports.Airport

data class Flight(
    val depart: Airport,
    val arrive: Airport,
    var favourite: Boolean
)


@Composable
fun HomeScreen(
    viewModel: FlightSearchViewModel,
    searchUiState: SearchUiState
) {
    val currentInput by viewModel.userInput.collectAsState()

    Scaffold(
        topBar =  {
            Text(
                stringResource(R.string.app_name),
                fontSize = 24.sp,
                modifier = Modifier.systemBarsPadding().padding(dimensionResource(R.dimen.medium_padding))
            )
        }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            EditQueryField(
                value = currentInput,
                onValueChange = {
                    viewModel.updateUserInput(it)
                    if (it == "") {
                        viewModel.updateEmpty()
                    } else {
                        viewModel.updateSuggestionList(it)
                    }
                }
            )
            when(searchUiState) {
                is SearchUiState.Input ->
                    AirportsList(
                        airports = searchUiState.suggestionList,
                        viewModel = viewModel,
                    )
                is SearchUiState.Query ->
                    FlightsList(
                        flights = searchUiState.queryList,
                        pageTitle = stringResource(R.string.query_list),
                        viewModel = viewModel,
                    )
                is SearchUiState.Empty ->
                    FlightsList(
                        flights = searchUiState.favouriteList,
                        pageTitle = stringResource(R.string.favourites_list),
                        viewModel = viewModel,
                    )
                is SearchUiState.Loading ->
                    FlightsList(
                        flights = emptyList(),
                        pageTitle = stringResource(R.string.loading),
                        viewModel = viewModel,
                    )
            }
        }
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
    LazyColumn(
        modifier,
        horizontalAlignment = Alignment.Start
    ) {
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
    viewModel: FlightSearchViewModel,
    pageTitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(pageTitle, modifier = Modifier.padding(dimensionResource(R.dimen.medium_padding)))
        LazyColumn {
            items(flights) { flight ->
                FlightCard(
                    flight,
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun FlightCard(
    flight: Flight,
    viewModel: FlightSearchViewModel,
    modifier: Modifier = Modifier
) {
    var isFavourite by remember { mutableStateOf(flight.favourite) }

    Card(
        modifier = Modifier.padding(dimensionResource(R.dimen.medium_padding)),
        elevation = CardDefaults.elevatedCardElevation(dimensionResource(R.dimen.default_card_elevation))
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(dimensionResource(R.dimen.medium_padding))) {
                Text(
                    stringResource(R.string.depart),
                    modifier = Modifier.padding(dimensionResource(R.dimen.small_padding))
                )
                Row(
                    modifier = Modifier.padding(dimensionResource(R.dimen.small_padding))
                ) {
                    Text(
                        flight.depart.iataCode,
                        modifier = Modifier.padding(end = dimensionResource(R.dimen.small_padding)),
                        fontWeight = FontWeight.Bold
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
                        modifier = Modifier.padding(end = dimensionResource(R.dimen.small_padding)),
                        fontWeight = FontWeight.Bold
                    )
                    Text(flight.arrive.name)
                }
            }
            FavouriteIcon(
                isFavourite = isFavourite,
                modifier = modifier
                    .align(Alignment.CenterVertically)
                    .size(dimensionResource(R.dimen.icon_size))
                    .padding(dimensionResource(R.dimen.large_padding))
                    .clickable {
                        isFavourite = !isFavourite
                        if(flight.favourite) {
                            viewModel.deleteFavourite(flight)
                        } else {
                            viewModel.addFavourite(flight)
                        }
                    }
            )
        }
    }
}

@Composable
fun FavouriteIcon(
    isFavourite: Boolean,
    modifier: Modifier = Modifier
) {
    if(isFavourite) {
        Icon(
            painter = painterResource(R.drawable.favourite),
            contentDescription = stringResource(R.string.remove_favourite),
            tint = Color.Red,
            modifier = modifier
        )
    } else {
        Icon(
            painter = painterResource(R.drawable.favourite),
            contentDescription = stringResource(R.string.favourite),
            modifier = modifier
        )
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
        favourite = true
    )
    FlightCard(mock, viewModel())
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
        favourite = true
    )
    FlightsList(listOf(mock, mock, mock, mock, mock), viewModel(), "Mock Flights lol")
}