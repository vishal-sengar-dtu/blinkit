package com.example.blinkit.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.blinkit.R
import com.example.blinkit.Utility
import com.example.blinkit.adapter.AdapterSkeleton
import com.example.blinkit.adapter.ProductAdapter
import com.example.blinkit.databinding.FragmentSearchBinding
import com.example.blinkit.viewmodel.UserViewModel
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var productAdapter: ProductAdapter
    private val viewModel : UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        val categoryName : String = arguments?.getString("CATEGORY_NAME") ?: "All Products"

        Utility.setStatusAndNavigationBarColor(
            requireActivity(),
            requireContext(),
            R.color.search_toolbar_bg,
            R.color.white
        )

        showSkeletonLoader()
        setProductRecyclerView(categoryName)
        searchProductFilter()


        return binding.root
    }

    private fun setProductRecyclerView(category : String) {
        productAdapter = ProductAdapter(this)

        lifecycleScope.launch {
            viewModel.fetchAllProducts(category).collect {
                productAdapter.differ.submitList(it)
                productAdapter.originalProductList = it
                binding.rvProducts.adapter = productAdapter

                hideSkeletonLoader()
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


    private fun searchProductFilter() {
        onSearchTextListener()
        onBackButtonClick()
        onCrossButtonClick()
    }

    private fun onSearchTextListener() {
        binding.etSearchBar.addTextChangedListener( object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.crossBtn.visibility = if(s.toString().isNotEmpty()) View.VISIBLE else View.GONE

                val query = s.toString().trim()
                productAdapter.getFilter().filter(query)
            }

            override fun afterTextChanged(s: Editable?) {}

        })
    }

    private fun onBackButtonClick() {
        binding.imgBack.setOnClickListener() {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun onCrossButtonClick() {
        binding.crossBtn.setOnClickListener {
            binding.etSearchBar.setText("")
        }
    }

}