package com.example.photosearcher.data

import com.example.photosearcher.network.SearcherApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val searcherRepository: SearcherRepository
}

class DefaultAppContainer : AppContainer {
    private val BASE_URL = "https://www.googleapis.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: SearcherApiService by lazy {
        retrofit.create(SearcherApiService::class.java)
    }

    override val searcherRepository: SearcherRepository by lazy {
        NetworkSearcherRepository(retrofitService)
    }
}