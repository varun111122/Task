package com.chiragtask.utils

import android.app.Application
import android.content.Context
import com.chiragtask.db.UserDatabase
import com.chiragtask.di.networkingModule
import com.chiragtask.di.repoModule
import com.chiragtask.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ApplicationClass : Application() {
    init {
        instance = this


    }

    companion object {
        var instance: ApplicationClass? = null
         var mDatabase: UserDatabase? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()

        mDatabase = UserDatabase.getInstance(this)

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@ApplicationClass)
            modules(listOf(networkingModule, viewModelModule, repoModule))
        }
    }

    public fun getAppDB(): UserDatabase {
        return mDatabase!!;
    }

}