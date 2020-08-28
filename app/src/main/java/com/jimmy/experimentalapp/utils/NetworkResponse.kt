package com.jimmy.experimentalapp.utils

import java.io.IOException

data class Response<out T>(val status: Status, val data: T?, val msg: String) {
    companion object {
        fun <T>success(data: T?): Response<T> = Response(Status.SUCCESS, data, "")
        fun <T>error(data: T?, msg: String): Response<T> = Response(Status.ERROR, data, msg)
        fun <T>loading(data: T?): Response<T> = Response(Status.LOADING, data, "")
    }
}

sealed class NetworkResponse<out T:Any, out U: Any> {
    data class Success<T: Any>(val response: Any): NetworkResponse<T, Nothing>()
    data class Failure<U: Any>(val body: Any, val code: Int): NetworkResponse<Nothing, U>()
    data class NetworkErr(val msg: IOException): NetworkResponse<Nothing, Nothing>()
    data class UnknownErr(val err: Throwable): NetworkResponse<Nothing, Nothing>()
}