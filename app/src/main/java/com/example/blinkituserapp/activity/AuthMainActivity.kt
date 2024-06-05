package com.example.blinkituserapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.blinkituserapp.R

class AuthMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
    }

}