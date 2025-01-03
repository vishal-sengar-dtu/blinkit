package com.example.blinkit

object Mapper {
    fun mapCategoriesDownloadUrl(categoriesDownloadUrlList : ArrayList<String?>) {
        categoriesDownloadUrlList.forEachIndexed { index, url ->
            when (index) {
                in 0..7 -> {
                    Constants.groceryCategoryList[index % 8].imageUrl = url
                }
                in 8..15 -> {
                    Constants.snacksCategoryList[index % 8].imageUrl = url
                }
                in 16..23 -> {
                    Constants.beautyCategoryList[index % 8].imageUrl = url
                }
                else -> {
                    Constants.householdCategoryList[index % 8].imageUrl = url
                }
            }
        }
    }
}