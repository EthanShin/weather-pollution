package com.example.weatherpollution

import com.example.weatherpollution.Data.DustData
import com.example.weatherpollution.Data.LocationData
import com.example.weatherpollution.Data.MsrstnData
import com.example.weatherpollution.Data.WeatherData
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


    @Headers("Authorization: KakaoAK " + BuildConfig.KAKAO_KEY)
    @GET("/v2/local/geo/transcoord.json")
    fun getTMlocation(
        @Query("x") x: Double,
        @Query("y") y: Double,
        @Query("output_coord") outputCoord: String
    ): Call<LocationData>

    @GET("/openapi/services/rest/MsrstnInfoInqireSvc/getNearbyMsrstnList")
    fun getMsrstnList(
        @Query("ServiceKey") serviceKey: String,
        @Query("tmX") x: String,
        @Query("tmY") y: String,
        @Query("_returnType") returnType: String
    ): Call<MsrstnData>

    @GET("/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty")
    fun getDustInfo(
        @Query("ServiceKey") serviceKey: String,
        @Query("stationName") station: String,
        @Query("dataTerm") dataTerm: String,
        @Query("_returnType") returnType: String,
        @Query("ver") version: String
    ): Call<DustData>
}