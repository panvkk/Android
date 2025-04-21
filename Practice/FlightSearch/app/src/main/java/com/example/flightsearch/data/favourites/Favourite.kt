package com.example.flightsearch.data.favourites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
data class Favourite (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "depart_code")
    val departCode: String,
    @ColumnInfo(name = "arrive_code")
    val arriveCode: String
)