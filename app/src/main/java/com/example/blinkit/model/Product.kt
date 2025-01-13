package com.example.blinkit.model

import com.example.blinkit.roomdb.CartProduct
import com.example.blinkit.utils.Utility

fun Product.toCartProduct() : CartProduct {
    return CartProduct(
        id = this.id ?: Utility.generateRandomId(),
        title = this.title,
        quantity = this.quantity,
        unit = this.unit,
        price = this.price,
        discount = this.discount,
        category = this.category,
        type = this.type,
        itemCount = this.itemCount,
        userId = this.userId,
        productImageUrl = this.productImageUrl?.get(0)
    )

}

data class Product(
    var id : String? = null,
    var title : String? = null,
    var quantity : Int? = null,
    var unit : String? = null,
    var price : Int? = null,
    var stock : Int? = null,
    var discount : Int? = null,
    var category : String? = null,
    var type : String? = null,
    var itemCount : Int = 0,
    var userId : String? = null,
    var productImageUrl : ArrayList<String?>? = null
)
