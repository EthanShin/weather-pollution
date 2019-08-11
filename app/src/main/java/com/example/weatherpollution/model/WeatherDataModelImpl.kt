package com.example.weatherpollution.model

import android.util.Log
import com.example.weatherpollution.BuildConfig.SERVICE_KEY
import com.example.weatherpollution.data.WeatherData
import com.example.weatherpollution.service.WeatherService
import io.reactivex.Single
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class WeatherDataModelImpl(private val service: WeatherService) : WeatherDataModel {

    override fun getData(nx: Int, ny: Int): Single<WeatherData> {
        val (baseDate, baseTime) = getBaseTime(LocalDateTime.now())
        Log.d("TEST", "baseDate & Time : $baseDate, $baseTime")

        return service.getWeatherInfoOfLocation(serviceKey = SERVICE_KEY, nx = nx, ny = ny, baseDate = baseDate, baseTime = baseTime, numOfRows = 10, pageNo = 1, type = "json")
    }

    data class TimeResult(val date: String, val time: String)
    private fun getBaseTime(date: LocalDateTime): TimeResult {

        var checkDate = date
        val h = date.hour
        val m = date.minute

        if (m < 40) {
            checkDate = if (h == 0) {
                checkDate.minusDays(1)
                    .also { checkDate.withHour(23) }
            } else {
                checkDate.withHour(h - 1)
            }
        }

        return TimeResult(
            checkDate.format(DateTimeFormatter.BASIC_ISO_DATE),
            checkDate.format(DateTimeFormatter.ofPattern("HHmm"))
        )
    }
}