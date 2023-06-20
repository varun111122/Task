package com.chiragtask.ui.createUsers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chiragtask.ui.dashboard.DashRepo
import com.chiragtask.base.BaseViewModel
import com.chiragtask.data.network.EventObserver
import com.chiragtask.data.prefrence.PreferenceDataStore
import com.chiragtask.db.UserData
import com.chiragtask.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateViewModel(private val repository: DashRepo) : BaseViewModel() {


    val inputFirstName = MutableLiveData<String>()
    val inputLastName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()


    var handleEvent = MutableLiveData<EventObserver<String>>()

    val eventState: LiveData<EventObserver<String>>
        get() = handleEvent


    fun save() {
        val firstName = inputFirstName.value ?: ""
        val lastName = inputLastName.value ?: ""
        val email = inputEmail.value ?: ""

        if (firstName.isNullOrBlank()) {
            handleEvent.value = EventObserver("User first name is empty")
        } else if (lastName.isNullOrBlank()) {
            handleEvent.value = EventObserver("User last name is empty")
        } else if (email.isNullOrBlank()) {
            handleEvent.value = EventObserver("email is empty")
        } else {

            insertSubscriber(UserData(0, firstName, lastName, email))
            inputFirstName.value = ""
            inputLastName.value = ""
            inputEmail.value = ""

        }
    }


    private fun insertSubscriber(userData: UserData) = viewModelScope.launch(Dispatchers.IO) {
        val insertAtRow = repository.addUser(userData)
        withContext(Dispatchers.Main) {
            handleEvent.value = EventObserver("Data inserted")
        }

    }





}