package com.example.weatherpollution.data.db

class CurrentWeatherRepository(private val currentWeatherDao: CurrentWeatherDao) {

    fun getCurrentWeather(): CurrentWeather {
        return currentWeatherDao.getCurrentWeather()
    }

    fun insertCurrentWeather(weather: CurrentWeather) {
        currentWeatherDao.insertCurrentWeather(weather)
    }

    fun deleteCurrentWeather() {
        currentWeatherDao.deleteCurrentWeather()
    }
}