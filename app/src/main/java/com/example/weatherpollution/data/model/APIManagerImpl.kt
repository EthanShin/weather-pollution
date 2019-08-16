package com.example.weatherpollution.data.model

import com.example.weatherpollution.BuildConfig.SERVICE_KEY
import com.example.weatherpollution.data.RetrofitAPI
import com.example.weatherpollution.data.remote.WeatherData
import com.example.weatherpollution.data.remote.ForecastResponse
import io.reactivex.Single

class APIManagerImpl(private val service: RetrofitAPI) : APIManager {

    override fun getWeatherData(x: Double, y: Double): Single<WeatherData> {

        return service.getWeatherInfoOfLocation(serviceKey = SERVICE_KEY, lat = x, lon = y)
    }

    override fun getForecastData(x: Double, y: Double): Single<ForecastResponse> {

        return service.getForecastInfoOfLocation(serviceKey = SERVICE_KEY, lat = x, lon = y)
    }
}