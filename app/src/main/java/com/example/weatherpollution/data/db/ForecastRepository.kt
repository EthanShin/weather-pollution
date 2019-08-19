package com.example.weatherpollution.data.db

import android.util.Log

class ForecastRepository(private val forecastDao: ForecastDao) {
    fun getForecast(): List<Forecast> {
        return forecastDao.getForecast()
    }

    suspend fun insertForecast(forecast: List<Forecast>) {
        forecastDao.insertForecast(forecast)
    }

    suspend fun deleteForecast() {
        forecastDao.deleteForecast()
    }

    fun getRowCount(): Int {
        return forecastDao.getRowCount()
    }
}