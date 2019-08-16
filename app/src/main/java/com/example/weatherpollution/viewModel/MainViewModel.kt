package com.example.weatherpollution.viewModel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherpollution.base.BaseViewModel
import com.example.weatherpollution.data.remote.WeatherData
import com.example.weatherpollution.data.db.CurrentWeather
import com.example.weatherpollution.data.db.CurrentWeatherRepository
import com.example.weatherpollution.data.model.WeatherDataModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val model: WeatherDataModel,
    private val repository: CurrentWeatherRepository
) : BaseViewModel() {

    private val _weatherLiveData = MutableLiveData<CurrentWeather>()
    val weatherLiveData: LiveData<CurrentWeather>
        get() = _weatherLiveData

    @SuppressLint("CheckResult")
    fun getWeather(x: Double, y: Double) {
        addDisposable(model.getData(x = x, y = y)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                insertWeather(it)

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