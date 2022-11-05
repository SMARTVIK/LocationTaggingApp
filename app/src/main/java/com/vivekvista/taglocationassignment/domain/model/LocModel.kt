package com.vivekvista.taglocationassignment.domain.model

import com.vivekvista.taglocationassignment.data.local.model.LocationEntity

/*
This is the actual data class being used inside the project
*/
data class LocModel (
    val locationName: String,
    val locationCoordinates: String
) {
    fun toLocationEntityModel(): LocationEntity {
        return LocationEntity(
            locationName = locationName,
            locationCoordinates = locationCoordinates
        )
    }
}