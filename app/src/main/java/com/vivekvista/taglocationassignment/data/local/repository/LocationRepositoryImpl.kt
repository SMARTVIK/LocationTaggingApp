package com.vivekvista.taglocationassignment.data.local.repository

import com.vivekvista.taglocationassignment.data.local.db.LocationDao
import com.vivekvista.taglocationassignment.data.local.model.LocationEntity
import com.vivekvista.taglocationassignment.domain.model.LocModel
import com.vivekvista.taglocationassignment.domain.repository.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationDao: LocationDao
) : LocationRepository {
    override suspend fun saveLocation(locModel: LocModel) {
        locationDao.insert(locModel.toLocationEntityModel())
    }

    override suspend fun getLocationByCoordinates(latitude: Double, longitude: Double) : LocationEntity{
        val locationCoordinatesString = "$latitude,$longitude"
        return locationDao.getLocationByCoordinates(locationCoordinatesString)
    }
}