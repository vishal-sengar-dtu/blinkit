package com.example.blinkit.repository

import com.example.blinkit.Firebase
import com.example.blinkit.model.Product
import com.example.blinkit.roomdb.CartProduct
import com.example.blinkit.roomdb.CartProductDao
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext

class HomeRepository(private val cartProductDao: CartProductDao) {

    companion object {
        const val ADMIN_PATH : String = "Admins"
        const val ALL_PRODUCTS : String = "AllProducts"
        const val PRODUCT_CATEGORY : String = "ProductCategory"
    }

    suspend fun insertCartProduct(product : CartProduct) {
        withContext(Dispatchers.IO) {
            cartProductDao.insertCartProduct(product)
        }
    }

    suspend fun updateCartProduct(product : CartProduct) {
        withContext(Dispatchers.IO) {
            cartProductDao.updateCartProduct(product)
        }
    }

    suspend fun deleteCartProduct(product : CartProduct) {
        withContext(Dispatchers.IO) {
            cartProductDao.deleteCartProduct(product)
        }
    }

    fun fetchAllProducts(): Flow<List<Product>> = callbackFlow {
        val db = Firebase.getDatabaseInstance().getReference(ADMIN_PATH).child(ALL_PRODUCTS)

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
        val db = Firebase.getDatabaseInstance().getReference(ADMIN_PATH).child(PRODUCT_CATEGORY).child(category)

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