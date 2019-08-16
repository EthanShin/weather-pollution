package com.example.weatherpollution.di

import androidx.room.Room
import com.example.weatherpollution.data.db.CurrentWeatherDatabase
import com.example.weatherpollution.data.db.CurrentWeatherRepository
import com.example.weatherpollution.data.model.APIManager
import com.example.weatherpollution.data.model.APIManagerImpl
import com.example.weatherpollution.data.RetrofitAPI
import com.example.weatherpollution.viewModel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel {
        MainViewModel(get(), get())
    }
}

val retrofitModule = module {
    single<RetrofitAPI> {
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitAPI::class.java)
    }
}

val modelModule = module {
    factory<APIManager> {
        APIManagerImpl(get())
    }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), CurrentWeatherDatabase::class.java, "weather_database").build()
    }
}

val daoModule = module {
    single {
        get<CurrentWeatherDatabase>().currentWeatherDao()
    }
}

val repositoryModule = module {
    single {
        CurrentWeatherRepository(get())
    }
}


val appModule = listOf(viewModelModule, retrofitModule, modelModule, databaseModule, daoModule, repositoryModule)