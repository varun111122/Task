package com.chiragtask.utils

import android.app.Dialog
import android.content.Context

import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.widget.FrameLayout
import com.chiragtask.R

class ProgressDialog(private var mContext: Context, private var message: String?) :
    Dialog(mContext) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_progress)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        window?.run {
            setBackgroundDrawable(null)
            setLayout(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
            setGravity(Gravity.CENTER)
        }

    }


}