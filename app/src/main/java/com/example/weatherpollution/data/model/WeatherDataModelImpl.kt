package com.example.weatherpollution.data.model

import com.example.weatherpollution.BuildConfig.SERVICE_KEY
import com.example.weatherpollution.data.remote.WeatherData
import com.example.weatherpollution.data.WeatherService
import io.reactivex.Single

class WeatherDataModelImpl(private val service: WeatherService) : WeatherDataModel {

    override fun getData(x: Double, y: Double): Single<WeatherData> {

        return service.getWeatherInfoOfLocation(serviceKey = SERVICE_KEY, lat = x, lon = y)
    }
}