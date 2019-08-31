package com.example.weatherpollution.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrentWeatherDao {

    @Query("SELECT * FROM weather_table")
    fun getCurrentWeather(): CurrentWeather

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(weather: CurrentWeather)

    @Query("DELETE FROM weather_table")
    suspend fun deleteCurrentWeather()
}