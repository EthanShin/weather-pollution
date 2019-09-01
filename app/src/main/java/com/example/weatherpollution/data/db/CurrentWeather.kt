package com.example.weatherpollution.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class CurrentWeather(
    @PrimaryKey @ColumnInfo(name = "date_time") val dateTime: String = "",
    @ColumnInfo(name = "icon") val icon: String?,
    @ColumnInfo(name = "main") val main: String?,
    @ColumnInfo(name = "temp") val temp: String?,
    @ColumnInfo(name = "humidity") val humidity: String?
)