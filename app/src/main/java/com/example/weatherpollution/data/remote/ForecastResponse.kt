package com.example.weatherpollution.data.remote

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ForecastResponse(
    @SerializedName("list")
    var ForecastList: ArrayList<ForecastListResponse>? = null
): Serializable