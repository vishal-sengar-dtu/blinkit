package com.example.blinkit.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [CartProduct::class], version = 2, exportSchema = false)
abstract class LocalDatabase : RoomDatabase(){

    abstract fun cartProductDao() : CartProductDao

    companion object {
        @Volatile
        var INSTANCE : LocalDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Recreate the CartProduct table
                database.execSQL("""
            CREATE TABLE IF NOT EXISTS `CartProduct` (
                `id` INTEGER NOT NULL PRIMARY KEY,
                `name` TEXT NOT NULL,
                `price` REAL NOT NULL)
        """)
            }
        }

        fun getInstance(context : Context) : LocalDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    "BRINGIT_DATABASE"
                ).addMigrations(MIGRATION_1_2)
                    .build().also { INSTANCE = it }
            }
        }

    }
}