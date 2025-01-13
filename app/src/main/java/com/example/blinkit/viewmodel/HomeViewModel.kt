package com.example.blinkit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blinkit.repository.HomeRepository
import com.example.blinkit.model.Product
import com.example.blinkit.model.toCartProduct
import com.example.blinkit.roomdb.CartProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository): ViewModel() {

    private val _cartItemCount = MutableStateFlow<Int>(0)
    val cartItemCount : StateFlow<Int> get() = _cartItemCount

    private val _cartItemList = MutableStateFlow<List<CartProduct>>(emptyList())
    val cartItemList: StateFlow<List<CartProduct>> get() = _cartItemList

    fun setCartItemCount(itemCount : Int) {
        _cartItemCount.value = itemCount
    }

    fun incrementCartItemCount() {
        _cartItemCount.value = (_cartItemCount.value ?: 0) + 1
    }

    fun decrementCartItemCount() {
        if((_cartItemCount.value ?: 0) > 0) {
            _cartItemCount.value = (_cartItemCount.value ?: 0) - 1
        }
    }

    fun addProductToCart(product : Product) {
        _cartItemList.value = _cartItemList.value.toMutableList().apply {
            val existingProduct = find { it.id == product.id }

            if (existingProduct == null) {
                val cartProduct = product.toCartProduct()
                add(cartProduct)
                insertCartProductInDB(cartProduct)
            } else {
                existingProduct.itemCount = product.itemCount
                updateCartProductInDB(existingProduct)
            }
        }
    }

    fun removeProductFromCart(product : Product) {
        _cartItemList.value = _cartItemList.value.toMutableList().apply {
            val existingProduct = find { it.id == product.id }
            if(existingProduct != null) {
                if (existingProduct.itemCount > 1) {
                    existingProduct.itemCount = product.itemCount
                    updateCartProductInDB(existingProduct)
                } else {
                    remove(existingProduct)
                    removeCartProductFromDB(existingProduct)
                }
            }
        }
    }

    private fun insertCartProductInDB(product : CartProduct) {
        viewModelScope.launch {
            repository.insertCartProduct(product)
        }
    }

    private fun updateCartProductInDB(product : CartProduct) {
        viewModelScope.launch {
            repository.updateCartProduct(product)
        }
    }

    private fun removeCartProductFromDB(product : CartProduct) {
        viewModelScope.launch {
            repository.deleteCartProduct(product)
        }
    }

    // Use repository to fetch all products
    fun fetchAllProducts(): Flow<List<Product>> = repository.fetchAllProducts()

    // Use repository to fetch category-specific products
    fun fetchCategorySpecificProducts(category: String): Flow<List<Product>> = repository.fetchCategorySpecificProducts(category)

}
