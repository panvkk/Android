package com.example.flightsearch

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.flightsearch.data.AppContainer
import com.example.flightsearch.data.AppDataContainer
import com.example.flightsearch.data.UserPreferencesRepository

private const val USER_INPUT_PREFERENCE_NAME = "user_input_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = USER_INPUT_PREFERENCE_NAME
)


class FlightSearchApplication : Application() {
    lateinit var container: AppContainer
    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate() {
        super.onCreate()

        container = AppDataContainer(this)
        userPreferencesRepository = UserPreferencesRepository(dataStore)

    }
}