package com.vivekvista.taglocationassignment.di.module

import android.content.Context
import androidx.room.Room
import com.vivekvista.taglocationassignment.common.Constants
import com.vivekvista.taglocationassignment.data.local.db.LocationDao
import com.vivekvista.taglocationassignment.data.local.db.LocationDatabase
import com.vivekvista.taglocationassignment.data.local.repository.LocationRepositoryImpl
import com.vivekvista.taglocationassignment.domain.repository.LocationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesPropertyDatabase(
        @ApplicationContext context: Context
    ): LocationDatabase {
        return Room.databaseBuilder(
            context,
            LocationDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()
    }

    @Provides
    fun providesPropertyDao(propertyDatabase: LocationDatabase): LocationDao {
        return propertyDatabase.propertyDao()
    }

    @Provides
    fun providesRepository(locationDao: LocationDao): LocationRepository {
        return LocationRepositoryImpl(locationDao)
    }

}