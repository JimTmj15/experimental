package com.jimmy.experimentalapp.ui.view_model

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jimmy.experimentalapp.models.database.WeatherEntity
import com.jimmy.experimentalapp.models.network.Error
import com.jimmy.experimentalapp.models.network.Success
import com.jimmy.experimentalapp.repository.Repository
import com.jimmy.experimentalapp.utils.NetworkResponse
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(private val repository: Repository) : ViewModel() {

    private val _weather = MutableLiveData<NetworkResponse<Success, Error>>()
    val weatherData: LiveData<NetworkResponse<Success, Error>> get() = _weather

    init {
        fetchWeatherData()
    }

    private fun fetchWeatherData() {
        viewModelScope.launch {
//            _weather.postValue(Response.loading(null))
            repository.getWeather().let {
                _weather.postValue(it)
//                if (it.isSuccessful) {
//                    _weather.postValue(Response.success(it.body()))
//                } else {
//                    _weather.postValue(Response.error(null, it.errorBody().toString()))
//                }
            }
        }
    }

    fun getWeatherDataFromDatabase() {
        viewModelScope.launch {
            val result = repository.getAllWeatherData()
            println("get database result ${result.size}")
        }
    }

    fun insertWeatherDataIntoDb(data: WeatherEntity) {
        viewModelScope.launch {
            repository.insertWeatherData(data)
        }
    }
}