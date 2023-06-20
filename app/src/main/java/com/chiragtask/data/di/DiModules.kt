package com.chiragtask.di

import androidx.room.Room
import com.chiragtask.ui.dashboard.DashRepoImp
import com.chiragtask.data.network.getApiProvider
import com.chiragtask.data.network.getApiService
import com.chiragtask.db.UserDatabase
import com.chiragtask.ui.createUsers.CreateViewModel
import com.chiragtask.ui.dashboard.DashboardViewModel
import com.chiragtask.ui.weather.WeatherRepo
import com.chiragtask.ui.weather.WeatherRepoImp
import com.chiragtask.ui.weather.WeatherViewModel
import com.chiragtask.utils.ApplicationClass
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val databaseModule = module {
    single { Room.databaseBuilder(get(), UserDatabase::class.java, "myUserTableDatabase").build() }
    single { get<UserDatabase>().dao }
}

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
    viewModel { DashboardViewModel(get()) }
    viewModel { CreateViewModel(get()) }
}

val repoModule = module {
    single<WeatherRepo> { WeatherRepoImp(get()) }
    single { DashRepoImp(get()) }
}



