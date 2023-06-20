package com.chiragtask.ui.dashboard


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chiragtask.base.BaseViewModel
import com.chiragtask.data.network.EventObserver
import com.chiragtask.db.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DashboardViewModel(private val repository: DashRepo) : BaseViewModel() {

    var handleEvent = MutableLiveData<EventObserver<String>>()

    val eventState: LiveData<EventObserver<String>>
        get() = handleEvent


    var allUsers = repository.allUser

    fun deleteSubscriber(userData: UserData) = viewModelScope.launch(Dispatchers.IO) {
        val deleteRow = repository.deleteUser(userData)
        withContext(Dispatchers.Main) {
            handleEvent.value = EventObserver("Delete in $deleteRow")

        }
    }

     fun clearAllSubscriber() = viewModelScope.launch(Dispatchers.IO) {
        val noOfDeletedRow = repository.deleteAllSubscriber()
        withContext(Dispatchers.Main) {
            handleEvent.value = EventObserver("All data clear")

        }
    }
}