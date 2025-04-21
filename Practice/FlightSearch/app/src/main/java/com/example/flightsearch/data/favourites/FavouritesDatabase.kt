package com.example.flightsearch.data.favourites

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Favourite::class), version = 1, exportSchema = false)
abstract class FavouritesDatabase : RoomDatabase() {
    abstract fun airportDao() : FavouriteDao

    companion object {
        @Volatile
        private var Instance: FavouritesDatabase? = null

        fun getDatabase(context: Context) : FavouritesDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, FavouritesDatabase::class.java, "favourites_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}