package com.vivekvista.taglocationassignment.domain.repository

import com.vivekvista.taglocationassignment.domain.model.Property

interface LocationRepository {
    suspend fun saveLocation(property: Property)
}