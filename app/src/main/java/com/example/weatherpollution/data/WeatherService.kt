package com.example.weatherpollution.data

import com.example.weatherpollution.data.remote.WeatherData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("/data/2.5/weather")
    fun getWeatherInfoOfLocation(
        @Query("APPID") serviceKey: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric"
    ): Single<WeatherData>
}