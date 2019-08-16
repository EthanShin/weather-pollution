package com.example.weatherpollution.data.remote

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MainResponse(
    var humidity: Double? = null,
    var pressure: Double? = null,
    var temp: Double? = null,
    @SerializedName("temp_min")
    var tempMin: Double? = null,
    @SerializedName("temp_max")
    var tempMax: Double? = null
): Serializable