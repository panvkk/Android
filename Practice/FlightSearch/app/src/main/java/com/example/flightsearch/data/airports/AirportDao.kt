package com.example.flightsearch.data.airports

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {
    @Query("SELECT * FROM airport " +
            "WHERE iata_code LIKE '%' || :input || '%'" +
            "OR name LIKE '%' || :input || '%'")
    suspend fun getAirportSuggestions(input: String) : List<Airport>

    @Query("SELECT * FROM airport")
    suspend fun getAllAirports() : List<Airport>
}