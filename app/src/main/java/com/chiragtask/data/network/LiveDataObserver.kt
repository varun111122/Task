package com.chiragtask.data.network

import android.util.Log
import androidx.lifecycle.Observer

interface LiveDataObserver : Observer<EventObserver<Resource>> {
    fun <T> onResponseSuccess(value: T, apiCode: String)
    fun onException(isNetworkAvailable: Boolean, exception: ApiError, apiCode: String)

    fun onError(error: ToastData, apiCode: String?)
    fun onLoading(message: String, apiCode: String?)
    fun onRetry(apiCode: String, networkError: Boolean, exception: ApiError)

    override fun onChanged(event: EventObserver<Resource>) {
        Log.d("API_RESPONSE", "first response")

        event.getContentIfNotHandled()?.let { apiResponse ->
            Log.d("API_RESPONSE", "handled response $apiResponse")
            when (apiResponse) {
                is Resource.Success<*> -> {
                    onResponseSuccess(apiResponse.value, apiResponse.apiCode)
                }
                is Resource.Failure -> {
                    onException(
                        apiResponse.isNetworkError,
                        apiResponse.exception,
                        apiResponse.apiCode
                    )
                }
                is Resource.Error -> {
                    onError(apiResponse.error, apiResponse.apiCode)
                }
                is Resource.Loading -> {
                    onLoading(apiResponse.message ?: "", apiResponse.apiCode)
                }
                is Resource.Retry -> {
//                    Log.d("BaseFragment",
//                        "onException: ${apiResponse.exception.message}  ${apiResponse.apiCode} ")
                    onRetry(apiResponse.apiCode, apiResponse.isNetworkError, apiResponse.exception)

                }

            }
        }

    }
}