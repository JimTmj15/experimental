package com.jimmy.experimentalapp.repository.api

import com.jimmy.experimentalapp.models.network.Error
import com.jimmy.experimentalapp.models.network.Success
import com.jimmy.experimentalapp.utils.NetworkResponse

interface ApiHelper {
    suspend fun getWeather(): NetworkResponse<Success, Error>
}