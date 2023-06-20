package com.chiragtask.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert
    suspend fun addUser(userData: UserData): Long

//    @Update
//    suspend fun updateUser(userData: UserData): Int

    @Delete
    suspend fun deleteUser(userData: UserData): Int

    @Query("DELETE FROM myUserTable")
    suspend fun deleteAlluser(): Int

    @Query("SELECT * FROM myUserTable")
    fun getListOfUser(): LiveData<List<UserData>>

}