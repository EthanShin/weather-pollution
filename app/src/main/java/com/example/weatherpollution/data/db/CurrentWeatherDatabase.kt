package com.example.weatherpollution.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CurrentWeather::class], version = 1, exportSchema = false)
abstract class CurrentWeatherDatabase : RoomDatabase() {

    abstract fun currentWeatherDao(): CurrentWeatherDao
}