package com.chiragtask.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "myUserTable")
data class UserData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userId")
    val id: Int,

    @ColumnInfo(name = "userFirstName")
    var firstName: String,

    @ColumnInfo(name = "userLastName")
    var lastName: String,

    @ColumnInfo(name = "userEmail")
    var email: String
)
