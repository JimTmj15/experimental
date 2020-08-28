package com.jimmy.experimentalapp.repository.api

import com.jimmy.experimentalapp.models.network.Error
import com.jimmy.experimentalapp.models.network.Success
import com.jimmy.experimentalapp.utils.NetworkResponse
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun  getWeather(): NetworkResponse<Success, Error> = apiService.getWeather()

}