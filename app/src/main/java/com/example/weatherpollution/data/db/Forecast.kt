package com.example.weatherpollution.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecast_table")
data class Forecast(
    @PrimaryKey @ColumnInfo(name = "date_time") val dateTime: String = "",
    @ColumnInfo(name = "icon") val icon: String?,
    @ColumnInfo(name = "main") val main: String?,
    @ColumnInfo(name = "temp") val temp: Double? = 0.0,
    @ColumnInfo(name = "humidity") val humidity: Double? = 0.0
)