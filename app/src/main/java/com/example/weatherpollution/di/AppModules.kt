package com.example.weatherpollution.di

import androidx.room.Room
import com.example.weatherpollution.data.db.DatabaseManager
import com.example.weatherpollution.data.db.CurrentWeatherRepository
import com.example.weatherpollution.data.db.ForecastRepository
import com.example.weatherpollution.data.model.APIManager
import com.example.weatherpollution.data.model.APIManagerImpl
import com.example.weatherpollution.data.RetrofitAPI
import com.example.weatherpollution.viewModel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel {
        MainViewModel(get(), get(), get())
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
        Room.databaseBuilder(androidContext(), DatabaseManager::class.java, "database")
            .fallbackToDestructiveMigration()
            .build()
    }
}

val daoModule = module {
    single {
        get<DatabaseManager>().currentWeatherDao()
    }
    single {
        get<DatabaseManager>().forecastDao()
    }
}

val repositoryModule = module {
    single {
        CurrentWeatherRepository(get())
    }
    single {
        ForecastRepository(get())
    }
}

val appModule = listOf(viewModelModule, retrofitModule, modelModule, databaseModule, daoModule, repositoryModule)