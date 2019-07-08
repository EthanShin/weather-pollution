package com.example.weatherpollution

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherApplication : Application() {

    var service: Service? = null

    override fun onCreate() {
        super.onCreate()

        setupRetrofit()
    }

    private fun setupRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://newsky2.kma.go.kr/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(Service::class.java)
    }

    fun requestService(): Service? {
        return service
    }
}