package com.example.blinkit

import android.content.Context
import android.content.SharedPreferences

class SharedPreference private constructor(private val sharedPref: SharedPreferences) {

    // Save login session
    fun saveLoginSession(status: Boolean) {
        with(sharedPref.edit()) {
            putBoolean("isLoggedIn", status)
            apply()
        }
    }

    // Check if the user is logged in
    fun isUserLoggedIn(): Boolean {
        return sharedPref.getBoolean("isLoggedIn", false)
    }

    companion object {
        @Volatile
        private var instance: SharedPreference? = null

        // Singleton instance getter
        fun getInstance(context: Context): SharedPreference {
            return instance ?: synchronized(this) {
                instance ?: SharedPreference(
                    context.applicationContext.getSharedPreferences("UserSession", Context.MODE_PRIVATE)
                ).also { instance = it }
            }
        }
    }
}
