package com.example.blinkit

import com.example.blinkit.roomdb.CartProduct

enum class Direction {
    UP, DOWN
}

interface CartListener {
    fun updateCartUI(itemCount : Int)
    fun cartAnimation(direction : Direction)
    fun setCartBottomSheet(cartList : List<CartProduct>)
}