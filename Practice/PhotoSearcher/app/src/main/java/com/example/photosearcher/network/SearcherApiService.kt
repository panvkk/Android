package com.example.photosearcher.network

import com.example.photosearcher.model.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface SearcherApiService {
    @GET("customsearch/v1")
    suspend fun getPhotos(
        @Query("q") query: String,
        @Query("cx") customSearchEngineId: String,
        @Query("key") apiKey: String,
        @Query("fields") searchingFields: String
    ): SearchResult
}