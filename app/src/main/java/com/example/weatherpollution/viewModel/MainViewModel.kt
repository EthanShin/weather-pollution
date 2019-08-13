package com.example.weatherpollution.viewModel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherpollution.base.BaseViewModel
import com.example.weatherpollution.data.WeatherData
import com.example.weatherpollution.model.WeatherDataModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val model: WeatherDataModel) : BaseViewModel() {

    private val _weatherLiveData = MutableLiveData<WeatherData>()
    val weatherLiveData: LiveData<WeatherData>
        get() = _weatherLiveData

    @SuppressLint("CheckResult")
    fun getWeather(x: Double, y: Double) {
        CompositeDisposable().add(model.getData(x = x, y = y)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _weatherLiveData.postValue(it)


            }, {
                Log.d("TEST", "Error message: ${it.message}")
            })
        )
    }
}