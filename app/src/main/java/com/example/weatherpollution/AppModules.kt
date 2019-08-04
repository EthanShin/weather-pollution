package com.example.weatherpollution

import com.example.weatherpollution.viewModel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainViewModel()
    }
}

val appModule = listOf(viewModelModule)