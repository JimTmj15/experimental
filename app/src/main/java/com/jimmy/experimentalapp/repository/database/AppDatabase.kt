package com.jimmy.experimentalapp.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jimmy.experimentalapp.models.database.WeatherEntity

@Database(entities = [WeatherEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}