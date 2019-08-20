package com.example.weatherpollution.viewModel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherpollution.base.BaseViewModel
import com.example.weatherpollution.data.remote.WeatherResponse
import com.example.weatherpollution.data.db.CurrentWeather
import com.example.weatherpollution.data.db.CurrentWeatherRepository
import com.example.weatherpollution.data.db.Forecast
import com.example.weatherpollution.data.db.ForecastRepository
import com.example.weatherpollution.data.model.APIManager
import com.example.weatherpollution.data.remote.ForecastResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val model: APIManager,
    private val weatherRepository: CurrentWeatherRepository,
    private val forecastRepository: ForecastRepository
) : BaseViewModel() {

    private val _weatherLiveData = MutableLiveData<CurrentWeather>()
    val weatherLiveData: LiveData<CurrentWeather>
        get() = _weatherLiveData

    private val _forecastLiveData = MutableLiveData<List<Forecast>>()
    val forecastLiveData: LiveData<List<Forecast>>
        get() = _forecastLiveData

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

                insertForecast(it)

            }, {
                Log.d("TEST", "Error message: ${it.message}")
            })
        )
    }

    private fun insertWeather(it: WeatherResponse) = CoroutineScope(Dispatchers.IO).launch {
        weatherRepository.deleteCurrentWeather()
        weatherRepository.insertCurrentWeather(it.toWeatherDB())
        _weatherLiveData.postValue(weatherRepository.getCurrentWeather())
    }

    private fun insertForecast(it: ForecastResponse) = CoroutineScope(Dispatchers.IO).launch {
        forecastRepository.deleteForecast()
        forecastRepository.insertForecast(it.toForecastDB())
        _forecastLiveData.postValue(forecastRepository.getForecast())
    }
}