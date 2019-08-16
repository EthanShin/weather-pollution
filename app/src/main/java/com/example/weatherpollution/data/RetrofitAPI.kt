package com.example.weatherpollution.data

import com.example.weatherpollution.data.remote.ForecastResponse
import com.example.weatherpollution.data.remote.WeatherData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {

    @GET("/data/2.5/weather")
    fun getWeatherInfoOfLocation(
        @Query("APPID") serviceKey: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric"
    ): Single<WeatherData>

    @GET("/data/2.5/forecast")
    fun getForecastInfoOfLocation(
        @Query("APPID") serviceKey: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric",
        @Query("cnt") cnt: Int = 8
    ): Single<ForecastResponse>
}