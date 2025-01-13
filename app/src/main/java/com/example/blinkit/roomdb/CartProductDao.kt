package com.example.blinkit.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CartProductDao {
    @Insert
    suspend fun insertCartProduct(product : CartProduct)

    @Update
    suspend fun updateCartProduct(product : CartProduct)

    @Delete
    suspend fun deleteCartProduct(product : CartProduct)
}