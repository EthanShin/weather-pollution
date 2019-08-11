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
    fun getWeather(nx: Int, ny: Int) {
        CompositeDisposable().add(model.getData(nx = nx, ny = ny)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("TEST", "success: ${it.response?.body?.items?.item?.get(0)?.baseDate}")
                _weatherLiveData.postValue(it)

            }, {
                Log.d("TEST", "Error message: ${it.message}")
            })
        )
    }
}