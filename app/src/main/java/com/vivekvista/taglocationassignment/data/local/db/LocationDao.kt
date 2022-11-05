package com.vivekvista.taglocationassignment.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.vivekvista.taglocationassignment.data.local.model.LocationEntity


@Dao
interface LocationDao {

    @Query("SELECT * FROM location")
    suspend fun getAllLocations(): List<LocationEntity>

    @Query("SELECT * FROM location WHERE location_coordinates=:latitude")
    suspend fun getLocationByCoordinates(latitude: String) : LocationEntity

    @Insert
    suspend fun insert(location: LocationEntity)

    @Delete
    suspend fun deleteLocation(location: LocationEntity)

    @Update
    suspend fun updateLocation(location: LocationEntity)
}