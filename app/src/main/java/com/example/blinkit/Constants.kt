package com.example.blinkit

import com.example.blinkit.model.Category

object Constants {
    val groceryCategoryList = mutableListOf(
        Category("Atta, Rice & Dal", null),
        Category("Bakery & Biscuits", null),
        Category("Chicken, Meat & Fish", null),
        Category("Dairy, Bread & Eggs", null),
        Category("Dry Fruits & Cereals", null),
        Category("Kitchenware & Appliances", null),
        Category("Oil, Ghee & Masala", null),
        Category("Vegetables & Fruits", null),
    )

    val snacksCategoryList = mutableListOf(
        Category("Chips & Namkeen", null),
        Category("Drinks & Juices", null),
        Category("Ice Creams & More", null),
        Category("Instant Food", null),
        Category("Paan Corner", null),
        Category("Sauces & Spreads", null),
        Category("Sweets & Chocolates", null),
        Category("Tea, Coffee & Milk Drinks", null)
    )

    val beautyCategoryList = mutableListOf(
        Category("Baby Care", null),
        Category("Bath & Body", null),
        Category("Beauty & Cosmetics", null),
        Category("Feminine Hygiene", null),
        Category("Hair", null),
        Category("Health & Pharma", null),
        Category("Sexual Wellness", null),
        Category("Skin & Face", null)
    )

    val householdCategoryList = mutableListOf(
        Category("Cleaners & Repellents",null),
        Category("Electronics", null),
        Category("Home & Lifestyle", null),
        Category("Stationery & Games", null)
    )
}