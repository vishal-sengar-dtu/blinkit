package com.example.blinkit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.blinkit.Firebase
import com.example.blinkit.model.Product
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow

class HomeViewModel: ViewModel() {
    private val _cartItemCount = MutableLiveData<Int>(0)
    val cartItemCount : LiveData<Int> get() = _cartItemCount

    private val _cartItemList = MutableStateFlow<List<Product>>(emptyList())
    val cartItemList: StateFlow<List<Product>> get() = _cartItemList

    fun incrementCartItemCount() {
        _cartItemCount.value = (_cartItemCount.value ?: 0) + 1
    }

    fun decrementCartItemCount() {
        if((_cartItemCount.value ?: 0) > 0) {
            _cartItemCount.value = (_cartItemCount.value ?: 0) - 1
        }
    }

    fun addProductToCart(product : Product) {
        _cartItemList.value += product
    }

    fun removeProductFromCart(product : Product) {
        _cartItemList.value -= product
    }

    fun fetchAllProducts(): Flow<List<Product>> = callbackFlow {
        val db = Firebase.getDatabaseInstance().getReference("Admins").child("AllProducts")

        val eventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val products = ArrayList<Product>()
                for(obj in snapshot.children) {
                    val product = obj.getValue(Product::class.java)
                    products.add(product!!)
                }
                trySend(products)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        db.addValueEventListener(eventListener)

        awaitClose { db.removeEventListener(eventListener) }

    }

    fun fetchCategorySpecificProducts(category: String) : Flow<List<Product>> = callbackFlow {
        val db = Firebase.getDatabaseInstance().getReference("Admins").child("ProductCategory").child(category)

        val eventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val products = ArrayList<Product>()
                for(obj in snapshot.children) {
                    val product = obj.getValue(Product::class.java)
                    products.add(product!!)
                }
                trySend(products)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        db.addValueEventListener(eventListener)

        awaitClose { db.removeEventListener(eventListener) }

    }

}
