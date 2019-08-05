package com.example.weatherpollution.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.weatherpollution.BuildConfig.SERVICE_KEY
import com.example.weatherpollution.WeatherService
import com.example.weatherpollution.data.Weather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val service: WeatherService) : ViewModel() {

    fun test() {
        service.getWeatherInfoOfLocation(serviceKey = SERVICE_KEY, nx = 59, ny = 125, baseDate = "20190804", baseTime = "1600", numOfRows = 10, pageNo = 1, type = "json")
            ?.enqueue(object: Callback<Weather> {
                override fun onFailure(call: Call<Weather>, t: Throwable) {
                    Log.d("TEST", "response_fail: $t")
                }

                override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                    var weatherData = response.body()
                    var items = weatherData?.response?.body?.items?.item
                    Log.d("TEST", "body: " + items?.get(0)?.baseDate)
                }
            })


    }
}