package com.example.amphibians.data

import com.example.amphibians.model.AmphibianInfo
import com.example.amphibians.network.AmphibiansApiService

interface AmphibiansInfoRepository {
    suspend fun getAmphibiansInfo() : List<AmphibianInfo>
}

class NetworkAmphibiansInfoRepository(
    private val amphibiansApiService: AmphibiansApiService
) : AmphibiansInfoRepository {
    override suspend fun getAmphibiansInfo() : List<AmphibianInfo> = amphibiansApiService.getInfo()
}