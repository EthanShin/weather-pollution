package com.example.weatherpollution.data.remote

import android.annotation.SuppressLint
import com.example.weatherpollution.data.db.Forecast
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.text.SimpleDateFormat

data class ForecastResponse(
    @SerializedName("list")
    var forecastList: ArrayList<ForecastListResponse>? = null
): Serializable {

    @SuppressLint("SimpleDateFormat")
    fun toForecastDB(): List<Forecast> {

        var result = mutableListOf<Forecast>()
        for (i in 0 until (forecastList?.size ?: 0)) {
            var value = forecastList?.getOrNull(i)
            result.add(
                Forecast(
                    icon = value?.weather?.getOrNull(0)?.icon,
                    main = value?.weather?.getOrNull(0)?.main,
                    temp = value?.main?.temp,
                    humidity = value?.main?.humidity,
                    dateTime = SimpleDateFormat("MM-dd HH:mm").format(value?.dateTime?.times(1000))
                )
            )
        }
        return result
    }
}