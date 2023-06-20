package com.chiragtask.ui

import android.os.Bundle
import com.chiragtask.R
import com.chiragtask.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}