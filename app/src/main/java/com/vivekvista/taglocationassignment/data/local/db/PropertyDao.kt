package com.vivekvista.taglocationassignment.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.vivekvista.taglocationassignment.data.local.model.PropertyEntity


@Dao
interface PropertyDao {

    @Query("SELECT * FROM property")
    suspend fun getAll(): List<PropertyEntity>

    @Insert
    suspend fun insert(property: PropertyEntity)
}