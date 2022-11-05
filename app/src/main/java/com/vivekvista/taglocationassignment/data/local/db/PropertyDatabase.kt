package com.vivekvista.taglocationassignment.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vivekvista.taglocationassignment.data.local.model.PropertyEntity

@Database(entities = [PropertyEntity::class], version = 1)
abstract class PropertyDatabase: RoomDatabase() {
    abstract fun propertyDao(): PropertyDao
}