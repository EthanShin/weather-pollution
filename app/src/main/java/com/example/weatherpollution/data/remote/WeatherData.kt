package com.example.weatherpollution.data.remote

import com.example.weatherpollution.data.db.CurrentWeather
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WeatherData(
    var main: Main? = null,
    @SerializedName("weather")
    var currentWeatherList: ArrayList<Weather>? = null
): Serializable {

    fun toWeatherDB(): CurrentWeather {
        return CurrentWeather(
            id = 0,
            main = currentWeatherList?.getOrNull(0)?.main,
            temp = main?.temp,
            humidity = main?.humidity
        )
    }
}

class Weather(
    var id: Int? = null,
    var description: String? = null,
    var icon: String? = null,
    var main: String? = null
): Serializable

class Main(
    var humidity: Double? = null,
    var pressure: Double? = null,
    var temp: Double? = null,
    @SerializedName("temp_min")
    var tempMin: Double? = null,
    @SerializedName("temp_max")
    var tempMax: Double? = null
): Serializable