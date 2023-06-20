package com.chiragtask.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chiragtask.data.network.ApiError
import com.chiragtask.data.network.EventObserver
import com.chiragtask.data.network.Resource
import kotlinx.coroutines.CoroutineExceptionHandler


abstract class BaseViewModel : ViewModel() {

    var LogTag = "BaseViewModel"


    private val _response = MutableLiveData<EventObserver<Resource>>()


    val coroutineExceptionHandle = CoroutineExceptionHandler { _, e ->
        Log.d(LogTag, "CoroutineExceptionHandler >> ${e.message}")

        updateResponseObserver(Resource.Failure(false, "", ApiError().apply {
            exception = e
        }))
    }

    fun getApiResponse(): LiveData<EventObserver<Resource>> = _response


    fun updateResponseObserver(response: Resource) {
        try {
            _response.postValue(EventObserver(response))
        } catch (e: Exception) {
            _response.value = EventObserver(response)
        }
    }







}