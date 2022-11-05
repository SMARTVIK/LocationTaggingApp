package com.vivekvista.taglocationassignment.domain.repository

import com.vivekvista.taglocationassignment.data.local.model.LocationEntity
import com.vivekvista.taglocationassignment.domain.model.LocModel

interface LocationRepository {
    suspend fun saveLocation(locModel: LocModel)
    suspend fun getLocationByCoordinates(location: Double, longitude: Double): LocationEntity
}