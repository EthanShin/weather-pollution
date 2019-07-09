package com.example.weatherpollution

import com.example.weatherpollution.Data.LocationData
import com.example.weatherpollution.Data.WeatherData
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Service {

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
    ): Call<WeatherData>

    @GET("/service/SecndSrtpdFrcstInfoService2/ForecastSpaceData")
    fun get3hoursWeatherInfoOfLocation(
        @Query("ServiceKey") serviceKey: String,
        @Query("base_date") baseDate: String,
        @Query("base_time") baseTime: String,
        @Query("nx") nx: Int,
        @Query("ny") ny: Int,
        @Query("numOfRows") numOfRows: Int,
        @Query("pageNo") pageNo: Int,
        @Query("_type") type: String
    ): Call<WeatherData>


    @Headers("Authorization: KakaoAK ****")
    @GET("/v2/local/geo/transcoord.json")
    fun getTMlocation(
        @Query("x") x: Double,
        @Query("y") y: Double,
        @Query("output_coord") outputCoord: String
    ): Call<LocationData>

    @GET("/openapi/services/rest/MsrstnInfoInqireSvc/getNearbyMsrstnList")
    fun getMsrstnList(
        @Query("tmX") x: Double,
        @Query("tmY") y: Double
    ): Call<JsonObject>
}