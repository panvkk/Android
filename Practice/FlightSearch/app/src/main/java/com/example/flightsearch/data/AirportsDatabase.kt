package com.example.flightsearch.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class AirportsDatabase() : RoomDatabase() {
    abstract fun airportDao() : AirportDao

    companion object {
        @Volatile
        private var Instance: AirportsDatabase? = null

        fun getDatabase(context: Context) : AirportsDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AirportsDatabase::class.java, "airports_database")
                    .createFromAsset("database/flight_search.db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}