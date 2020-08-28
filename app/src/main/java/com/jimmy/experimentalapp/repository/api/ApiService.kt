package com.jimmy.experimentalapp.repository.api

import com.jimmy.experimentalapp.models.network.Error
import com.jimmy.experimentalapp.models.network.Success
import com.jimmy.experimentalapp.utils.NetworkResponse
import retrofit2.http.GET


interface ApiService {
    @GET("/data/2.5/weather?q=London,uk&appid=439d4b804bc8187953eb36d2a8c26a02")
    suspend fun getWeather(): NetworkResponse<Success, Error>
}
