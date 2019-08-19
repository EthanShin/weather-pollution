package com.example.weatherpollution.data.remote

import android.annotation.SuppressLint
import android.util.Log
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
            Log.d("TEST2", "$i: ${forecastList?.get(i)?.dateTime}")
            result.add(
                Forecast(
                    main = forecastList?.getOrNull(i)?.weather?.getOrNull(0)?.main,
                    temp = forecastList?.getOrNull(i)?.main?.temp,
                    humidity = forecastList?.getOrNull(i)?.main?.humidity,
                    dateTime = SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(forecastList?.getOrNull(i)?.dateTime?.times(1000))
                )
            )
        }
        return result
    }
}