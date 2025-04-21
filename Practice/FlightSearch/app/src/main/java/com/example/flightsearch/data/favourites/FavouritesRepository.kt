package com.example.flightsearch.data.favourites

import kotlinx.coroutines.flow.Flow

interface FavouritesRepository {
    fun getAllFavouritesStream() : Flow<List<Favourite>>

    suspend fun getByAirports(departCode: String, arriveCode: String) : List<Favourite>

    suspend fun deleteByAirports(departCode: String, arriveCode: String)

    suspend fun insert(favourite: Favourite)

    suspend fun delete(favourite: Favourite)

    suspend fun update(favourite: Favourite)
}