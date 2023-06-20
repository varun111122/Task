package com.chiragtask.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chiragtask.ui.createUsers.CreateViewModel
import com.chiragtask.ui.dashboard.DashRepo
import com.chiragtask.ui.dashboard.DashboardViewModel

class DashModelFactory(private val repository: DashRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(repository) as T
        }
        else if (modelClass.isAssignableFrom(CreateViewModel::class.java)) {
            return CreateViewModel(repository) as T
        }

        throw IllegalAccessException("Unknown view model")
    }
}