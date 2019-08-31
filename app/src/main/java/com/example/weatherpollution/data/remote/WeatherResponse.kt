package com.example.weatherpollution.data.remote

import android.annotation.SuppressLint
import com.example.weatherpollution.data.db.CurrentWeather
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.text.SimpleDateFormat

data class WeatherResponse(
    var main: MainResponse? = null,
    @SerializedName("weather")
    var currentWeatherList: ArrayList<WeatherListResponse>? = null,
    @SerializedName("dt")
    var dateTime: Long? = null
): Serializable {

    @SuppressLint("SimpleDateFormat")
    fun toWeatherDB(): CurrentWeather {
        return CurrentWeather(
            icon = currentWeatherList?.getOrNull(0)?.icon,
            main = currentWeatherList?.getOrNull(0)?.main,
            temp = main?.temp,
            humidity = main?.humidity,
            dateTime = SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(dateTime?.times(1000))
        )
    }
}