package com.example.photosearcher.data

import com.example.photosearcher.model.SearchResult
import com.example.photosearcher.network.SearcherApiService

interface SearcherRepository {
    suspend fun getPhotos(query: String) : SearchResult
}

class NetworkSearcherRepository(
    private val searcherApiService: SearcherApiService
) : SearcherRepository {
    override suspend fun getPhotos(query: String):
            SearchResult = searcherApiService.getPhotos(
                query = query,
                apiKey = "AIzaSyBu5rrZGACbdK_3E2v6SXQD60U30Ulaqr0",
                customSearchEngineId = "e3d09e108fa7a4516",
                searchingFields ="items(link)",
                searchType = "image"
            )
}