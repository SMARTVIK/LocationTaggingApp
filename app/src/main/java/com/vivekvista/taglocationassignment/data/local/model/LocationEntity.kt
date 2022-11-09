package com.vivekvista.taglocationassignment.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
data class LocationEntity(
    @PrimaryKey(autoGenerate = true)
    val locationId: Int = 0,

    @ColumnInfo(name = "location_name")
    val locationName: String,

    @ColumnInfo(name = "location_coordinates")
    val locationCoordinates: String


)
