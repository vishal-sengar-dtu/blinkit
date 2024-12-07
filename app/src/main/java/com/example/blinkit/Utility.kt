package com.example.blinkit

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import androidx.core.content.ContextCompat

object Utility {

    fun setStatusBarColor(activity : Activity, context : Context, color : Int) {
        activity.window?.apply {
            val statusBarColors = ContextCompat.getColor(context, color)
            statusBarColor = statusBarColors

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
        activity.window?.apply {
            navigationBarColor = ContextCompat.getColor(context, color)
        }
    }
}