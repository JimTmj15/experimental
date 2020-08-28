package com.jimmy.experimentalapp.repository

import com.jimmy.experimentalapp.models.database.WeatherEntity
import com.jimmy.experimentalapp.repository.api.ApiHelper
import com.jimmy.experimentalapp.repository.database.DatabaseHelper
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val dbHelper: DatabaseHelper
) {
    suspend fun getAllWeatherData() = dbHelper.getAllWeatherData()
    suspend fun insertWeatherData(data: WeatherEntity) = dbHelper.insertWeatherData(data)
    suspend fun getWeather() = apiHelper.getWeather()
}