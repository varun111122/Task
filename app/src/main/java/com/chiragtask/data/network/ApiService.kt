package com.chiragtask.data.network

import com.apiModel.WeatherModel
import com.chiragtask.utils.ApiEndPoint
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET(ApiEndPoint.API_WEATHER)
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") unit: String,
        @Query("appid") appid: String
    ): Response<WeatherModel>


}


