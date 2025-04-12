package com.example.flightsearch.data

import android.content.Context
import com.example.flightsearch.data.airports.AirportsDatabase
import com.example.flightsearch.data.favourites.FavouritesDatabase
import com.example.flightsearch.data.favourites.FavouritesRepository
import com.example.flightsearch.data.favourites.OfflineFavouritesRepository

interface AppContainer {
    val favouritesRepository: FavouritesRepository
    val database: AirportsDatabase
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val favouritesRepository by lazy {
        OfflineFavouritesRepository(FavouritesDatabase.getDatabase(context).airportDao())
    }
    override val database: AirportsDatabase by lazy {
        AirportsDatabase.getDatabase(context)
    }
}