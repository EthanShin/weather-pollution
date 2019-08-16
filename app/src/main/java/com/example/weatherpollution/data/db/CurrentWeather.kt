package com.example.weatherpollution.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_weather")
data class CurrentWeather(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "main") val main: String?,
    @ColumnInfo(name = "temp") val temp: Double? = 0.0,
    @ColumnInfo(name = "humidity") val humidity: Double? = 0.0
)