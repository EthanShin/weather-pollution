package com.example.weatherpollution

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    @GET("/service/SecndSrtpdFrcstInfoService2/ForecastSpaceData")
    fun getWeatherInfoOfLocation(
        @Query("ServiceKey") serviceKey: String,
        @Query("base_date") baseDate: String,
        @Query("base_time") baseTime: String,
        @Query("nx") nx: Int,
        @Query("ny") ny: Int,
        @Query("numOfRows") numOfRows: Int,
        @Query("pageNo") pageNo: Int,
        @Query("_type") type: String
    ): Call<WeatherData>
}