package com.example.weatherpollution

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherApplication : Application() {

    var weatherService: Service? = null
    var tmService: Service? = null

    override fun onCreate() {
        super.onCreate()

        setupWeatherRetrofit()
        setupTMRetrofit()
    }

    private fun setupWeatherRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://newsky2.kma.go.kr/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        weatherService = retrofit.create(Service::class.java)
    }

    private fun setupTMRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dapi.kakao.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        tmService = retrofit.create(Service::class.java)
    }

    fun requestService(serviceNumber: Int): Service? {
        return when(serviceNumber) {
            0 -> weatherService
            else -> tmService
        }
    }
}