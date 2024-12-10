package com.example.blinkit

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService

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

    fun showKeyboard(editText: EditText) {
        editText.requestFocus() // Ensure the EditText has focus
        val imm = editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    fun hideKeyboard(editText: EditText) {
        val imm = editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    fun showToast(context : Context, message : String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun saveLoginSession(context : Context, status : Boolean) {
        val sharedPref = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("isLoggedIn", status)
            apply()
        }
    }

    fun isUserLoggedIn(context: Context): Boolean {
        val sharedPref = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("isLoggedIn", false)
    }

    private fun clearLoginSession(context: Context) {
        val sharedPref = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            clear()
            apply()
        }
    }

}