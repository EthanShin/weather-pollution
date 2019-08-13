package com.example.weatherpollution.model

import com.example.weatherpollution.BuildConfig.SERVICE_KEY
import com.example.weatherpollution.data.WeatherData
import com.example.weatherpollution.service.WeatherService
import io.reactivex.Single

class WeatherDataModelImpl(private val service: WeatherService) : WeatherDataModel {

    override fun getData(x: Double, y: Double): Single<WeatherData> {

        return service.getWeatherInfoOfLocation(serviceKey = SERVICE_KEY, lat = x, lon = y)
    }
}