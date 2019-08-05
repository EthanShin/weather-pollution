package com.example.weatherpollution.di

import com.example.weatherpollution.WeatherService
import com.example.weatherpollution.viewModel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
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
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherService::class.java)
    }
}

val appModule = listOf(viewModelModule, retrofitModule)