package com.example.blinkit.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.blinkit.R
import com.example.blinkit.SharedPreference

class AuthActivity : AppCompatActivity() {
    lateinit var sharedPref : SharedPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = SharedPreference.getInstance(applicationContext)
        setContentView(R.layout.activity_main)
    }
}