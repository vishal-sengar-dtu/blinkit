package com.example.blinkit

import com.example.blinkit.model.Category

object Constants {
    val groceryCategoryList = arrayOf(
        Category("Vegetables & Fruits", R.drawable.vegetables_and_fruits),
        Category("Atta, Rice & Dal", R.drawable.atta_rice_dal),
        Category("Oil, Ghee & Masala", R.drawable.oil_ghee_masala),
        Category("Dairy, Bread & Eggs", R.drawable.dairy_bread_eggs),
        Category("Bakery & Biscuits", R.drawable.bakery_biscuit),
        Category("Dry Fruits & Cereals", R.drawable.dry_fruits_cereals),
        Category("Chicken, Meat & Fish", R.drawable.chicken_fish_meat),
        Category("Kitchenware & Appliances", R.drawable.kitchenware_appliances),
    )

    val snacksCategoryList = arrayOf(
        Category("Chips & Namkeen", R.drawable.chips_namkeen),
        Category("Sweets & Chocolates", R.drawable.sweets_chocolates),
        Category("Drinks & Juices", R.drawable.drinks_juices),
        Category("Tea, Coffee & Milk Drinks", R.drawable.tea_coffee),
        Category("Instant Food", R.drawable.instant_food),
        Category("Sauces & Spreads", R.drawable.sauces_spreads),
        Category("Paan Corner", R.drawable.paan_corner),
        Category("Ice Creams & More", R.drawable.ice_cream)
    )

    val beautyCategoryList = arrayOf(
        Category("Bath & Body", R.drawable.bath_soap),
        Category("Hair", R.drawable.hair),
        Category("Skin & Face", R.drawable.skin_face),
        Category("Beauty & Cosmetics", R.drawable.beauty_cosmetics),
        Category("Feminine Hygiene", R.drawable.feminine_hygiene),
        Category("Baby Care", R.drawable.baby_care),
        Category("Health & Pharma", R.drawable.health_pharma),
        Category("Sexual Wellness", R.drawable.sexual_wellness)
    )

    val householdCategoryList = arrayOf(
        Category("Home & Lifestyle", R.drawable.household),
        Category("Cleaners & Repellents", R.drawable.cleaner_repellant),
        Category("Electronics", R.drawable.electronics),
        Category("Stationery & Games", R.drawable.stationery_games),
    )
}