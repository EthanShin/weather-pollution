package com.example.weatherpollution.di

import com.example.weatherpollution.model.WeatherDataModel
import com.example.weatherpollution.model.WeatherDataModelImpl
import com.example.weatherpollution.service.WeatherService
import com.example.weatherpollution.viewModel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel {
        MainViewModel(get())
    }
}

val retrofitModule = module {
    single<WeatherService> {
        Retrofit.Builder()
            .baseUrl("http://newsky2.kma.go.kr/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherService::class.java)
    }
}

var modelModule = module {
    factory<WeatherDataModel> {
        WeatherDataModelImpl(get())
    }
}

val appModule = listOf(viewModelModule, retrofitModule, modelModule)