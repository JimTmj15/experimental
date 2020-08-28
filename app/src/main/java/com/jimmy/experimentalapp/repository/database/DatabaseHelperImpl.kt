package com.jimmy.experimentalapp.repository.database

import com.jimmy.experimentalapp.models.database.WeatherEntity
import javax.inject.Inject

class DatabaseHelperImpl @Inject constructor(private val database: AppDatabase): DatabaseHelper {
    override suspend fun getAllWeatherData(): List<WeatherEntity> = database.weatherDao().getAllWeatherData()
    override suspend fun insertWeatherData(data: WeatherEntity) = database.weatherDao().insertWeatherData(data)
}