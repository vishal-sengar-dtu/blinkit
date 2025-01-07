package com.example.blinkit.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.blinkit.R
import com.example.blinkit.Utility
import com.example.blinkit.adapter.AdapterSkeleton
import com.example.blinkit.adapter.ProductAdapter
import com.example.blinkit.databinding.FragmentCategoryBinding
import com.example.blinkit.viewmodel.UserViewModel
import kotlinx.coroutines.launch

class CategoryFragment : Fragment() {
    private lateinit var binding : FragmentCategoryBinding
    private val viewModel : UserViewModel by viewModels()
    private lateinit var productAdapter : ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(layoutInflater)

        Utility.setStatusAndNavigationBarColor(
            requireActivity(),
            requireContext(),
            R.color.home_yellow,
            R.color.white
        )

        val category = arguments?.getString("CATEGORY_NAME")
        binding.materialToolbar.title = category
        setProductRecyclerView(category!!)
        showSkeletonLoader()
        onBackButtonClick()
        onSearchButtonClick()


        return binding.root
    }

    private fun setProductRecyclerView(category : String) {
        productAdapter = ProductAdapter(this)

        lifecycleScope.launch {
            viewModel.fetchCategorySpecificProducts(category).collect {
                productAdapter.differ.submitList(it)
                productAdapter.originalProductList = it
                binding.rvProducts.adapter = productAdapter

                hideSkeletonLoader()

                if(it.isEmpty()) {
                    binding.tvNoProducts.visibility = View.VISIBLE
                } else {
                    binding.tvNoProducts.visibility = View.GONE
                }
            }
        }
    }

    private fun showSkeletonLoader() {
        binding.apply {
            rvProducts.visibility = View.GONE
            rvSkeletonProducts.visibility = View.VISIBLE
            val skeletonList = List(9)  {""}
            binding.rvSkeletonProducts.adapter = AdapterSkeleton(skeletonList)
        }
    }

    private fun hideSkeletonLoader() {
        binding.apply {
            rvSkeletonProducts.visibility = View.GONE
            rvProducts.visibility = View.VISIBLE
        }
    }


    private fun onBackButtonClick() {
        binding.materialToolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_categoryFragment_to_homeFragment)
        }
    }

    private fun onSearchButtonClick() {
        binding.materialToolbar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.searchFragment -> {
                    findNavController().navigate(R.id.action_categoryFragment_to_searchFragment)
                    return@setOnMenuItemClickListener true
                }

                else -> {
                    return@setOnMenuItemClickListener false
                }
            }
        }

    }

}