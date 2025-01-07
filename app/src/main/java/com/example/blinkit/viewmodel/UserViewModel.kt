package com.example.blinkit.viewmodel

import androidx.lifecycle.ViewModel
import com.example.blinkit.Firebase
import com.example.blinkit.model.Product
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class UserViewModel: ViewModel() {

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
