package com.jimmy.experimentalapp.models.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Success(
    @Json(name = "id")
    val id: String = "",

    @Json(name ="name")
    val name:String = "",

    @Json(name = "cod")
    val cod: String = "",

    @Json(name = "coord")
    val coord: Coord
)

data class Coord(
    @Json(name = "lon")
    val lon: String = "",

    @Json(name = "lat")
    val lat: String = ""
)

@JsonClass(generateAdapter = true)
data class Error(
    @Json(name = "status")
    val status: String,

    @Json(name = "message")
    val message: String
)
