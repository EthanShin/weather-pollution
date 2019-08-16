package com.example.weatherpollution.data.remote

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ForecastData(
    @SerializedName("list")
    var ForecastList: ArrayList<Forecast>? = null
): Serializable

class Forecast(
    @SerializedName("dt_txt")
    var dateTime: String? = null
): Serializable