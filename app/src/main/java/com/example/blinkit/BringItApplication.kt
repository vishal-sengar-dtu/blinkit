package com.example.blinkit

import android.app.Application
import com.example.blinkit.roomdb.CartProductDao
import com.example.blinkit.roomdb.LocalDatabase

class BringItApplication: Application() {
    private lateinit var localDatabase: LocalDatabase
    lateinit var sharedPreference : SharedPreference

    override fun onCreate() {
        super.onCreate()

        localDatabase = LocalDatabase.getInstance(this)
        sharedPreference = SharedPreference.getInstance(this)
    }

    fun getCartProductDao() : CartProductDao {
        return localDatabase.cartProductDao()
    }
}