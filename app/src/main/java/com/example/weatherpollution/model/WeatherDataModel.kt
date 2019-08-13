package com.example.weatherpollution.model

import com.example.weatherpollution.data.WeatherData
import io.reactivex.Single

interface WeatherDataModel {

    fun getData(x: Double, y: Double) : Single<WeatherData>
}