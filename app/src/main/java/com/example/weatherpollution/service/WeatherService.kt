package com.example.weatherpollution.service

import com.example.weatherpollution.data.WeatherData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("/service/SecndSrtpdFrcstInfoService2/ForecastGrib")
    fun getWeatherInfoOfLocation(
        @Query("ServiceKey") serviceKey: String,
        @Query("base_date") baseDate: String,
        @Query("base_time") baseTime: String,
        @Query("nx") nx: Int,
        @Query("ny") ny: Int,
        @Query("numOfRows") numOfRows: Int,
        @Query("pageNo") pageNo: Int,
        @Query("_type") type: String
    ): Single<WeatherData>
}