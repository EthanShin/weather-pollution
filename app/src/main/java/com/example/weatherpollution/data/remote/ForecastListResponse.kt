package com.example.weatherpollution.data.remote

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ForecastListResponse (
    var main: MainResponse? = null,
    var weather: ArrayList<WeatherListResponse>? = null,
    @SerializedName("dt")
    var dateTime: Long? = null
): Serializable