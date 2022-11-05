package com.vivekvista.taglocationassignment.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vivekvista.taglocationassignment.data.local.model.LocationEntity

@Database(entities = [LocationEntity::class], version = 1)
abstract class LocationDatabase: RoomDatabase() {
    abstract fun propertyDao(): LocationDao
}