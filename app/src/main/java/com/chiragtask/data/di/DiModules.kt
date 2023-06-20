package com.chiragtask.di

import com.chiragtask.ui.dashboard.DashRepo
import com.chiragtask.data.network.getApiProvider
import com.chiragtask.data.network.getApiService
import com.chiragtask.ui.weather.WeatherRepo
import com.chiragtask.ui.weather.WeatherRepoImp
import com.chiragtask.ui.weather.WeatherViewModel
import com.chiragtask.utils.ApplicationClass
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module


val networkingModule = module {
    single {
        getApiProvider()
    }
    single {
        getApiService(get())
    }

    single { getDatabase() }
}

fun getDatabase() = ApplicationClass.mDatabase
fun getAppContext() = ApplicationClass.applicationContext()

val viewModelModule = module {
    viewModel { WeatherViewModel(get()) }
}


val repoModule = module {
    single<WeatherRepo> { WeatherRepoImp(get()) }
    single { DashRepo(get()) }
}



