package com.example.blinkit

interface CartListener {
    fun updateCartUI(itemCount : Int)
    fun cartAnimation(popUp : Boolean)
}