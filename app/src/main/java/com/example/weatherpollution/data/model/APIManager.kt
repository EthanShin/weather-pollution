package com.example.weatherpollution.data.model

import com.example.weatherpollution.data.remote.ForecastResponse
import com.example.weatherpollution.data.remote.WeatherResponse
import io.reactivex.Single

interface APIManager {

    fun getWeatherData(x: Double, y: Double) : Single<WeatherResponse>

    fun getForecastData(x: Double, y: Double) : Single<ForecastResponse>
}