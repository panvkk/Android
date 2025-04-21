package com.example.flightsearch.data.favourites

import kotlinx.coroutines.flow.Flow

class OfflineFavouritesRepository(val favouritesDao: FavouriteDao) : FavouritesRepository {
    override fun getAllFavouritesStream() : Flow<List<Favourite>> = favouritesDao.getAllFavouritesStream()

    override suspend fun getByAirports(departCode: String, arriveCode: String) : List<Favourite> = favouritesDao.getByAirports(departCode, arriveCode)

    override suspend fun deleteByAirports(departCode: String, arriveCode: String) = favouritesDao.deleteByAirports(departCode = departCode, arriveCode = arriveCode)

    override suspend fun insert(favourite: Favourite) = favouritesDao.insert(favourite)

    override suspend fun delete(favourite: Favourite) = favouritesDao.delete(favourite)

    override suspend fun update(favourite: Favourite) = favouritesDao.update(favourite)
}