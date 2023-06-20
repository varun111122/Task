package com.chiragtask.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.chiragtask.base.BaseViewModel
import com.chiragtask.data.network.EventObserver
import com.chiragtask.data.prefrence.PreferenceDataStore
import com.chiragtask.utils.Constants
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel() : BaseViewModel() {

    val inputUserName = MutableLiveData<String>()
    val inputPassword = MutableLiveData<String>()


    var handleEvent = MutableLiveData<EventObserver<String>>()

    val eventState: LiveData<EventObserver<String>>
        get() = handleEvent


    fun checkLogin() {
        val userName = inputUserName.value
        val password = inputPassword.value

        if (userName.isNullOrBlank()) {
            handleEvent.value = EventObserver("User name is empty")
        } else if (password.isNullOrBlank()) {
            handleEvent.value = EventObserver("Password is empty")
        } else if (userName == "testapp@google.com" && password == "Test@123456") {

            viewModelScope.launch {
                withContext(viewModelScope.coroutineContext) {
                    PreferenceDataStore.saveBoolean(Constants.IS_LOGGED_IN, true)
                }
            }
            handleEvent.value = EventObserver("login")

        } else {
            handleEvent.value =
                EventObserver("Please enter username as testapp@google.com and password as Test@123456")

        }


    }


}