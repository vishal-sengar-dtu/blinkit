package com.example.blinkit.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("CartProduct")
data class CartProduct (
    @PrimaryKey
    val id : String = "random",
    val title : String?,
    var quantity : Int?,
    var unit : String?,
    var price : Int?,
    var discount : Int?,
    var category : String?,
    var type : String?,
    var itemCount : Int = 0,
    var userId : String?,
    var productImageUrl : String?
)