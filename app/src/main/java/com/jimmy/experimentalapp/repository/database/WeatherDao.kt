package com.jimmy.experimentalapp.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jimmy.experimentalapp.models.database.WeatherEntity

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weatherentity")
    suspend fun getAllWeatherData(): List<WeatherEntity>

    @Insert
    suspend fun insertWeatherData(data: WeatherEntity)
}