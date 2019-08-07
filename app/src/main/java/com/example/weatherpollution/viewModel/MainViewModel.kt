package com.example.weatherpollution.viewModel

import android.annotation.SuppressLint
import android.util.Log
import com.example.weatherpollution.base.BaseViewModel
import com.example.weatherpollution.model.WeatherDataModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val model: WeatherDataModel) : BaseViewModel() {

    @SuppressLint("CheckResult")
    fun test(nx: Int, ny: Int) {
        CompositeDisposable().add(model.getData(nx = nx, ny = ny)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("TEST", "success: ${it.response?.body?.items?.item?.get(0)?.baseDate}")
            }, {
                Log.d("TEST", "Error message: ${it.message}")
            })
        )
    }
}