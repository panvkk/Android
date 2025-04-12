package com.example.flightsearch.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsearch.R

data class Airport(
    val iataCode: String,
    val name: String
)

data class Flight(
    val Depart: Airport,
    val Arrive: Airport
)


@Composable
fun HomeScreen() {
    Scaffold() { contentPadding ->
        val paddings = contentPadding
    }
}

@Composable
fun AirportsList(
    airports: List<Airport>,
    pageTitle: String,
    modifier: Modifier = Modifier
) {
    Column {
        Text(pageTitle)
        LazyColumn {
            items(airports) { airport ->
                AirportRow(
                    airport,
                    modifier = Modifier.padding(dimensionResource(R.dimen.medium_padding))
                )
            }
        }
    }
}

@Composable
fun FlightsList(
    flights: List<Flight>,
    pageTitle: String,
    modifier: Modifier = Modifier
) {
    Column {
        Text(pageTitle)
        LazyColumn {
            items(flights) { flight ->
                FlightCard(
                    flight,
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.medium_padding))
                        .fillMaxWidth()
                )
            }
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
fun FlightCard(
    flight: Flight,
    modifier: Modifier = Modifier
) {
    Card(
        modifier,
        elevation = CardDefaults.elevatedCardElevation(dimensionResource(R.dimen.default_card_elevation))
    ) {
        Column {
            Text(
                stringResource(R.string.depart),
                modifier = Modifier.padding(dimensionResource(R.dimen.small_padding))
            )
            Row(
                modifier = Modifier.padding(dimensionResource(R.dimen.small_padding))
            ) {
                Text(
                    flight.Depart.iataCode,
                    modifier = Modifier.padding(end = dimensionResource(R.dimen.small_padding))
                    )
                Text(flight.Depart.name)
            }
            Text(
                stringResource(R.string.arrive),
                modifier = Modifier.padding(dimensionResource(R.dimen.small_padding))
            )
            Row(
                modifier = Modifier.padding(dimensionResource(R.dimen.small_padding))
            ) {
                Text(
                    flight.Arrive.iataCode,
                    modifier = Modifier.padding(end = dimensionResource(R.dimen.small_padding))
                )
                Text(flight.Arrive.name)
            }
        }
    }
}

@Preview
@Composable
fun FlightCardPreview() {
    val mock = Flight(
        Depart = Airport(
            iataCode = "SVO",
            name = "Sheremet'evo Airport"
        ),
        Arrive = Airport(
            iataCode = "LAX",
            name = "Los Angeles Airport"
        )
    )
    FlightCard(mock)
}

@Preview
@Composable
fun FlightsListPreview() {
    val mock = Flight(
        Depart = Airport(
            iataCode = "SVO",
            name = "Sheremet'evo Airport"
        ),
        Arrive = Airport(
            iataCode = "LAX",
            name = "Los Angeles Airport"
        )
    )
    FlightsList(listOf(mock, mock, mock, mock, mock), "Mock Flights lol")
}