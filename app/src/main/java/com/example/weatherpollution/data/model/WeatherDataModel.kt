package com.example.weatherpollution.data.model

import com.example.weatherpollution.data.remote.WeatherData
import io.reactivex.Single

interface WeatherDataModel {

    fun getData(x: Double, y: Double) : Single<WeatherData>
}