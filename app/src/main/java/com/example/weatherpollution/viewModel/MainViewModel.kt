package com.example.weatherpollution.viewModel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.weatherpollution.BuildConfig.SERVICE_KEY
import com.example.weatherpollution.service.WeatherService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val service: WeatherService) : ViewModel() {

    @SuppressLint("CheckResult")
    fun test() {
        service.getWeatherInfoOfLocation(serviceKey = SERVICE_KEY, nx = 59, ny = 125, baseDate = "20190806", baseTime = "1100", numOfRows = 10, pageNo = 1, type = "json")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("TEST", "success: ${it.response?.body?.items?.item?.get(0)?.baseDate}")
            }, {
                Log.d("TEST", "Error message: ${it.message}")
            })
    }
}