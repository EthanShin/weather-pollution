package com.example.weatherpollution

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val date = LocalDateTime.now()  // 현재시간
        val (baseDate, baseTime) = getBaseTime(date) // 날짜와 시간분리 ex)20190707, 0500

        Log.d("getBaseTime", "date: $baseDate, time: $baseTime")

        startActivity(Intent(this, WeatherActivity::class.java))
    }

    data class TimeResult(val date: String, val time: String)

    private fun getBaseTime(date: LocalDateTime): TimeResult {

        var checkDate = date

        val h = date.hour   // 공공데이터포털의 데이터가 2시를 기준으로 날짜가 변경됨
        if (h < 2) {
            checkDate = checkDate.minusDays(1)  // 2시가 되지 않았다면 전날 23시로 날짜 보정
            checkDate = checkDate.withHour(23)
        } else {
            checkDate = checkDate.withHour(h - (h + 1) % 3) // 3시간 간격으로 기준시간이 변경됨
        }

        return TimeResult(checkDate.format(DateTimeFormatter.BASIC_ISO_DATE), checkDate.format(DateTimeFormatter.ofPattern("HHmm")))
    }
}