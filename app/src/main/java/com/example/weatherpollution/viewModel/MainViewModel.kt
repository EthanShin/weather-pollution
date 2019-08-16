package com.example.weatherpollution.viewModel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherpollution.base.BaseViewModel
import com.example.weatherpollution.data.remote.WeatherData
import com.example.weatherpollution.data.db.CurrentWeather
import com.example.weatherpollution.data.db.CurrentWeatherRepository
import com.example.weatherpollution.data.model.APIManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class MainViewModel(
    private val model: APIManager,
    private val repository: CurrentWeatherRepository
) : BaseViewModel() {

    private val _weatherLiveData = MutableLiveData<CurrentWeather>()
    val weatherLiveData: LiveData<CurrentWeather>
        get() = _weatherLiveData

    fun getData(x: Double, y: Double) {
        getWeather(x, y)
        getForecast(x, y)
    }

    @SuppressLint("CheckResult")
    fun getWeather(x: Double, y: Double) {
        addDisposable(model.getWeatherData(x = x, y = y)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                insertWeather(it)

            }, {
                Log.d("TEST", "Error message: ${it.message}")
            })
        )
    }

    @SuppressLint("CheckResult", "SimpleDateFormat")
    fun getForecast(x: Double, y: Double) {
        addDisposable(model.getForecastData(x = x, y = y)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                val dateTime = it.ForecastList?.get(0)?.dateTime?.times(1000)

                dateTime?.let {
                    Log.d("TEST", SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(dateTime))

                }

            }, {
                Log.d("TEST", "Error message: ${it.message}")
            })
        )
    }

    private fun insertWeather(it: WeatherData) = CoroutineScope(Dispatchers.IO).launch {
        repository.deleteCurrentWeather()
        repository.insertCurrentWeather(it.toWeatherDB())
        _weatherLiveData.postValue(repository.getCurrentWeather())
    }
}