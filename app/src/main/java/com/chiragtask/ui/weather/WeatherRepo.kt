package com.chiragtask.ui.weather

import com.chiragtask.data.network.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepo {
    suspend fun getWeather(lat: Double, lon: Double, unit: String, appid: String): Flow<Resource>

}