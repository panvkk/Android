package com.example.amphibians.network

import com.example.amphibians.model.AmphibianInfo

interface AmphibiansApiService {
    suspend fun getInfo() : List<AmphibianInfo>
}