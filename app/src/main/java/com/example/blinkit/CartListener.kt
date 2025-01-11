package com.example.blinkit

enum class Direction {
    UP, DOWN
}

interface CartListener {
    fun updateCartUI(itemCount : Int)
    fun cartAnimation(direction : Direction)
}