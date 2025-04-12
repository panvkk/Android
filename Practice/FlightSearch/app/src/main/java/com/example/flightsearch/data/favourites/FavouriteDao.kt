package com.example.flightsearch.data.favourites

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {

    @Query("SELECT * FROM favourites")
    fun getAllFavouritesStream() : Flow<List<Favourite>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favourite: Favourite)

    @Delete
    suspend fun delete(favourite: Favourite)

    @Update
    suspend fun update(favourite: Favourite)

}