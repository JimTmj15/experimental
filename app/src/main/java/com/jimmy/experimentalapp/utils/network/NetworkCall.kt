package com.jimmy.experimentalapp.utils.network

import com.jimmy.experimentalapp.utils.NetworkResponse
import okhttp3.Request
import okhttp3.ResponseBody
import okio.IOException
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.lang.UnsupportedOperationException

class NetworkCall<S : Any, E : Any>(
    private val delegate: Call<S>,
    private val errorConverter: Converter<ResponseBody, E>
) : Call<NetworkResponse<S, E>> {

    override fun enqueue(callback: Callback<NetworkResponse<S, E>>) {
        delegate.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val body = response.body()
                val code = response.code()
                val error = response.errorBody()

                if (response.isSuccessful) {
                    if (body != null) {
                        callback.onResponse(
                            this@NetworkCall,
                            Response.success(NetworkResponse.Success(body))
                        )
                    } else {
                        // Response is successful but the body is null
                        callback.onResponse(
                            this@NetworkCall,
                            Response.success(NetworkResponse.UnknownErr(Throwable()))
                        )
                    }
                } else {
                    val errorBody = when {
                        error == null -> null
                        error.contentLength() == 0L -> null
                        else -> try {
                            errorConverter.convert(error)
                        } catch (ex: Exception) {
                            null
                        }
                    }
                    if (errorBody != null) {
                        callback.onResponse(
                            this@NetworkCall,
                            Response.success(NetworkResponse.Failure(errorBody, code))
                        )
                    } else {
                        callback.onResponse(
                            this@NetworkCall,
                            Response.success(NetworkResponse.UnknownErr(Throwable()))
                        )
                    }
                }
            }

            override fun onFailure(call: Call<S>, throwable: Throwable) {
                val networkResponse = when (throwable) {
                    is IOException -> NetworkResponse.NetworkErr(throwable)
                    else -> NetworkResponse.UnknownErr(throwable)
                }
                callback.onResponse(this@NetworkCall, Response.success(networkResponse))
            }
        })
    }

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun timeout(): Timeout = delegate.timeout()

    override fun clone(): Call<NetworkResponse<S, E>> = NetworkCall(delegate.clone(), errorConverter)

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<NetworkResponse<S, E>> {
        throw UnsupportedOperationException("Doesn't support execute operation")
    }

    override fun request(): Request = delegate.request()

}