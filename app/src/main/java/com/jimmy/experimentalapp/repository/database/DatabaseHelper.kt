package com.jimmy.experimentalapp.repository.database

import androidx.room.Insert
import androidx.room.Query
import com.jimmy.experimentalapp.models.database.WeatherEntity

interface DatabaseHelper {
    suspend fun getAllWeatherData(): List<WeatherEntity>
    suspend fun insertWeatherData(data: WeatherEntity)
}

