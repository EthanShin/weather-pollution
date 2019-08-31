package com.example.weatherpollution.data.db

class CurrentWeatherRepository(private val currentWeatherDao: CurrentWeatherDao) {

    fun getCurrentWeather(): CurrentWeather {
        return currentWeatherDao.getCurrentWeather()
    }

    suspend fun insertCurrentWeather(weather: CurrentWeather) {
        currentWeatherDao.insertCurrentWeather(weather)
    }

    suspend fun deleteCurrentWeather() {
        currentWeatherDao.deleteCurrentWeather()
    }
}