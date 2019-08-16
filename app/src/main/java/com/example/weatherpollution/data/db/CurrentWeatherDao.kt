package com.example.weatherpollution.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrentWeatherDao {

    @Query("SELECT * FROM current_weather")
    fun getCurrentWeather(): CurrentWeather

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrentWeather(weather: CurrentWeather)

    @Query("DELETE FROM current_weather")
    fun deleteCurrentWeather()
}