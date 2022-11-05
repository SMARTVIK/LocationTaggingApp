package com.vivekvista.taglocationassignment.domain.model

import com.vivekvista.taglocationassignment.data.local.model.PropertyEntity

/*
This is the actual data class being used inside the project
*/
data class Property (
    val propertyName: String,
    val propertyCoordinates: String
) {
    fun toPropertyEntity(): PropertyEntity {
        return PropertyEntity(
            propertyName = propertyName,
            propertyCoordinates = propertyCoordinates
        )
    }
}