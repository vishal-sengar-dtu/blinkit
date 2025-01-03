package com.example.blinkit.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.blinkit.Firebase
import com.example.blinkit.Mapper
import kotlinx.coroutines.flow.MutableStateFlow

class HomeViewModel: ViewModel() {
    private val _isImageUrlLoaded = MutableStateFlow<Boolean>(false)
    val isImageUrlLoaded = _isImageUrlLoaded

    fun loadCategoryImageUrlsFromStorage() {
        val categoryImageUrlList = ArrayList<String?>()
        val categoryUriListReference = Firebase.getFirebaseStorageReference().child("categories")

        categoryUriListReference.listAll()
            .addOnSuccessListener { subCategoryList ->
                subCategoryList.prefixes.forEach { subCategory ->
                    subCategory.listAll()
                        .addOnSuccessListener { imageUriList ->
                            imageUriList.items.forEach { imageUri ->
                                imageUri.downloadUrl
                                    .addOnCompleteListener { task ->
                                        val url = task.result
                                        categoryImageUrlList.add(url.toString())
                                    }
                            }
                        }
                }
            }

        Mapper.mapCategoriesDownloadUrl(categoryImageUrlList)
    }

}
