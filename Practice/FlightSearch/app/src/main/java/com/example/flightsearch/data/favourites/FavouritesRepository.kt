package com.example.flightsearch.data.favourites

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

interface FavouritesRepository {
    fun getAllFavouritesStream() : Flow<List<Favourite>>

    suspend fun insert(favourite: Favourite)

    suspend fun delete(favourite: Favourite)

    suspend fun update(favourite: Favourite)
}