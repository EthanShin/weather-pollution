package com.example.weatherpollution.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ForecastDao {

    @Query("SELECT * FROM forecast_table")
    fun getForecast(): List<Forecast>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecast(forecast: List<Forecast>)

    @Query("DELETE FROM forecast_table")
    suspend fun deleteForecast()

    @Query("SELECT COUNT(date_time) FROM forecast_table")
    fun getRowCount(): Int
}