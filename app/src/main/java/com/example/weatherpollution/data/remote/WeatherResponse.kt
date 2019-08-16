package com.example.weatherpollution.data.remote

import com.example.weatherpollution.data.db.CurrentWeather
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WeatherData(
    var main: MainResponse? = null,
    @SerializedName("weather")
    var currentWeatherListResponseList: ArrayList<WeatherListResponse>? = null,
    @SerializedName("dt")
    var dateTime: Long? = null
): Serializable {

    fun toWeatherDB(): CurrentWeather {
        return CurrentWeather(
            id = 0,
            main = currentWeatherListResponseList?.getOrNull(0)?.main,
            temp = main?.temp,
            humidity = main?.humidity
        )
    }
}