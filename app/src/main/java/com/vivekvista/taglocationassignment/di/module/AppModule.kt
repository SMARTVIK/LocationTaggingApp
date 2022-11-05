package com.vivekvista.taglocationassignment.di.module

import android.content.Context
import androidx.room.Room
import com.vivekvista.taglocationassignment.common.Constants
import com.vivekvista.taglocationassignment.data.local.db.PropertyDao
import com.vivekvista.taglocationassignment.data.local.db.PropertyDatabase
import com.vivekvista.taglocationassignment.data.local.repository.LocationRepositoryImpl
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
    ): PropertyDatabase {
        return Room.databaseBuilder(
            context,
            PropertyDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()
    }

    @Provides
    fun providesPropertyDao(propertyDatabase: PropertyDatabase): PropertyDao {
        return propertyDatabase.propertyDao()
    }

    @Provides
    fun providesRepository(propertyDao: PropertyDao): LocationRepositoryImpl {
        return LocationRepositoryImpl(propertyDao)
    }



}