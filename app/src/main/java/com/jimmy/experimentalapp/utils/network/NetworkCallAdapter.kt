package com.jimmy.experimentalapp.utils.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import java.lang.reflect.Type

class NetworkCallAdapter<S : Any, E : Any>(
    private val respType: Type,
    private val errorConverter: Converter<ResponseBody, E>
) : CallAdapter<S, NetworkCall<S, E>> {

    override fun adapt(call: Call<S>): NetworkCall<S, E> = NetworkCall(call, errorConverter)

    override fun responseType(): Type = respType
}