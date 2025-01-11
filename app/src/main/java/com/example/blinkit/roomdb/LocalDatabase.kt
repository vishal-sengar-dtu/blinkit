package com.example.blinkit.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CartProduct::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase(){

    abstract fun cartProductDao() : CartProductDao

    companion object {
        @Volatile
        var INSTANCE : LocalDatabase? = null

        fun getInstance(context : Context) : LocalDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    "BRINGIT_DATABASE"
                ).build().also { INSTANCE = it }
            }
        }

    }
}