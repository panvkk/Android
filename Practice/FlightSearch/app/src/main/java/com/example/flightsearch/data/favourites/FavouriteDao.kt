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

    @Query("SELECT * FROM favourites " +
            "WHERE depart_code = :departCode " +
            "AND arrive_code = :arriveCode")
    suspend fun getByAirports(departCode: String, arriveCode: String) : List<Favourite>

    @Query("DELETE FROM favourites " +
            "WHERE depart_code = :departCode " +
            "AND arrive_code = :arriveCode")
    suspend fun deleteByAirports(departCode: String, arriveCode: String)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(favourite: Favourite)

    @Delete
    suspend fun delete(favourite: Favourite)

    @Update
    suspend fun update(favourite: Favourite)

}