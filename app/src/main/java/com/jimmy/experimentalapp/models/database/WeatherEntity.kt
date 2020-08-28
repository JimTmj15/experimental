package com.jimmy.experimentalapp.models.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "code") val code: String,
    @ColumnInfo(name = "longitude") val lon: String,
    @ColumnInfo(name = "latitude") val lat: String
)
