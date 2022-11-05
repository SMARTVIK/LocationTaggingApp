package com.vivekvista.taglocationassignment.data.local.repository

import com.vivekvista.taglocationassignment.data.local.db.PropertyDao
import com.vivekvista.taglocationassignment.domain.model.Property
import com.vivekvista.taglocationassignment.domain.repository.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val propertyDao: PropertyDao
) : LocationRepository {
    override suspend fun saveLocation(property: Property) {
        propertyDao.insert(property.toPropertyEntity())
    }
}