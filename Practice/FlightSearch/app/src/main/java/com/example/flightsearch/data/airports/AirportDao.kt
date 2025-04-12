package com.example.flightsearch.data.airports

import androidx.room.Dao
import androidx.room.Query

@Dao
interface AirportDao {
    @Query("SELECT * FROM airports " +
            "WHERE iata_code LIKE '%input%'" +
            "OR name LIKE '%input%'")
    fun getAirportSuggestions(input: String)

    @Query("SELECT * FROM airports")
    fun getAllAirports()
}