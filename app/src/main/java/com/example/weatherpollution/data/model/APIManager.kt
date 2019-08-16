package com.example.weatherpollution.data.model

import com.example.weatherpollution.data.remote.ForecastResponse
import com.example.weatherpollution.data.remote.WeatherData
import io.reactivex.Single

interface APIManager {

    fun getWeatherData(x: Double, y: Double) : Single<WeatherData>

    fun getForecastData(x: Double, y: Double) : Single<ForecastResponse>
}