package com.example.weatherpollution.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WeatherData(
    var main: Main? = null,
    var weatherList: ArrayList<Weather>? = null
): Serializable

class Weather(
    var description: String? = null,
    var icon: String? = null,
    var main: String? = null
): Serializable

class Main(
    var humidity: String? = null,
    var pressure: String? = null,
    var temp: Float? = null,
    @SerializedName("temp_min")
    var tempMin: Float? = null,
    @SerializedName("temp_max")
    var tempMax: Float? = null
): Serializable
