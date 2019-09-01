package com.example.weatherpollution.data.remote

import android.annotation.SuppressLint
import com.example.weatherpollution.data.db.Forecast
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.text.SimpleDateFormat
import kotlin.math.round

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
                    temp = value?.main?.temp?.let { round(it * 10) / 10 }.toString() + " \u2103",
                    humidity = value?.main?.humidity?.toInt().toString() + " %",
                    dateTime = SimpleDateFormat("MM-dd HH시").format(value?.dateTime?.times(1000))
                )
            )
        }
        return result
    }
}