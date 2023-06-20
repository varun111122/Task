package com.chiragtask.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chiragtask.data.network.ApiError
import com.chiragtask.data.network.LiveDataObserver
import com.chiragtask.data.network.ToastData
import com.chiragtask.utils.ProgressDialog


open class BaseActivity : AppCompatActivity(), LiveDataObserver {


    private var progressDialog: ProgressDialog? = null


    fun showProgressBar(message: String? = null) {
        if (!isDestroyed) {
            try {


                if (progressDialog?.isShowing == true) {
                    progressDialog?.dismiss()
                }
                progressDialog = null
                if (progressDialog == null) {
                    progressDialog = ProgressDialog(this, message)

                    progressDialog?.let { dialog ->
                        dialog.setCancelable(false)
                        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    }
                }
                progressDialog?.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    fun hideProgressBar() {
        progressDialog?.let { if (it.isShowing) it.dismiss() }
    }


    fun handleOnException(networkError: Boolean, exception: ApiError, apiCode: String) {

        if (isDestroyed || isFinishing)
            return

        exception.message?.let {
            Log.d("API_ERROR", it)
            showToastShort(it)
        }

        when (exception.statusCode) {


        }

    }


    fun showToastShort(message: String) {
        Log.d("SHOW_TOAST", message)
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)

        toast.show()
    }

    fun showToastLong(message: String) {
        Log.d("SHOW_TOAST", message)
        val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
//
        toast.show()
    }


    fun handleOnError(error: ToastData) {
        error.errorCode?.let {
            Log.d("ERROR", getString(it))
            showToastShort(getString(it))
        }
        error.errorString?.let {
            showToastShort(it)
        }

    }


    open fun onApiRetry(apiCode: String) {

    }


    override fun <T> onResponseSuccess(value: T, apiCode: String) {
        Log.d("BASE_ACTIVITY", "onResponseSuccess>>> $apiCode")
        hideProgressBar()
    }

    override fun onResume() {
        super.onResume()
//        initActivitySocket()
//        if (ActivityTrackSocketManager.isConnect()) {
//            sendSocketData()
//        } else {
//            initActivitySocket()
//        }

    }

    override fun onException(isNetworkAvailable: Boolean, exception: ApiError, apiCode: String) {
        hideProgressBar()
        handleOnException(isNetworkAvailable, exception, apiCode)
    }


    override fun onError(error: ToastData, apiCode: String?) {
        hideProgressBar()
        handleOnError(error)
    }


    override fun onLoading(message: String, apiCode: String?) {
        showProgressBar()
    }

    override fun onRetry(apiCode: String, networkError: Boolean, exception: ApiError) {

    }

}