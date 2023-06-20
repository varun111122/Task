package com.chiragtask.ui.weather

import com.apiModel.WeatherModel
import com.chiragtask.base.BaseRepo
import com.chiragtask.data.network.ApiService
import com.chiragtask.data.network.Resource
import com.chiragtask.utils.ApiEndPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class WeatherRepoImp(private val apiService: ApiService) : WeatherRepo {
    override suspend fun getWeather(
        lat: Double,
        lon: Double,
        unit: String,
        appid: String
    ): Flow<Resource> {
        return object : BaseRepo<WeatherModel>() {
            override suspend fun fetchDataFromRemoteSource(): Response<WeatherModel> {
                return apiService.getWeather(lat,lon,unit,appid)
            }
        }.safeApiCall(ApiEndPoint.API_WEATHER).flowOn(Dispatchers.IO)
    }
}