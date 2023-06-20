package com.chiragtask.ui.weather

import androidx.lifecycle.viewModelScope
import com.chiragtask.base.BaseViewModel
import com.chiragtask.data.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel(private val repo: WeatherRepo) : BaseViewModel() {


    fun getWeather() =
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.getWeather(12.9082847623315,77.65197822993314,"imperial","b143bb707b2ee117e62649b358207d3e")
            withContext(Dispatchers.IO) {
                response.collect {
                    if (it is Resource.Success<*>) {


                    }
                    updateResponseObserver(it)
                }
            }
        }

}