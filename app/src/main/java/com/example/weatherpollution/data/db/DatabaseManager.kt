package com.example.weatherpollution.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CurrentWeather::class, Forecast::class], version = 1, exportSchema = false)
abstract class DatabaseManager : RoomDatabase() {

    abstract fun currentWeatherDao(): CurrentWeatherDao

    abstract fun forecastDao(): ForecastDao
}