package com.chiragtask.ui.dashboard

import com.chiragtask.db.UserDao
import com.chiragtask.db.UserData

class DashRepoImp(private val dao: UserDao) {

    val allUser = dao.getListOfUser()

    suspend fun addUser(userData: UserData): Long {
        return dao.addUser(userData)
    }

    suspend fun deleteUser(userData: UserData): Int {
        return dao.deleteUser(userData)
    }

    suspend fun deleteAllSubscriber() :Int {
        return  dao.deleteAlluser()
    }




}
