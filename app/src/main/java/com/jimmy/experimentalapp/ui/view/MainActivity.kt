package com.jimmy.experimentalapp.ui.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.jimmy.experimentalapp.R
import com.jimmy.experimentalapp.models.database.WeatherEntity
import com.jimmy.experimentalapp.models.network.Error
import com.jimmy.experimentalapp.models.network.Success
import com.jimmy.experimentalapp.repository.api.ApiHelper
import com.jimmy.experimentalapp.ui.view_model.MainViewModel
import com.jimmy.experimentalapp.utils.NetworkResponse
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var apiHelper: ApiHelper

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.weatherData.observe(this, Observer {
            when(it) {
                is NetworkResponse.Success -> {
                    val result = it.response as Success
                    println("get network success result ${result}")
//                    viewModel.insertWeatherDataIntoDb(WeatherEntity(result.id, result.name, result.cod, result.coord.lat, result.coord.lon))
                    viewModel.getWeatherDataFromDatabase()
                }

                is NetworkResponse.NetworkErr -> {
                    val result = it.msg.localizedMessage
                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
                }

                is NetworkResponse.Failure -> {
                    val result = it.body as Error
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }

                is NetworkResponse.UnknownErr -> {
                    val result = it.err.localizedMessage
                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
