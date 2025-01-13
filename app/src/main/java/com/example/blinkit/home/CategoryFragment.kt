package com.example.blinkit.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.blinkit.R
import com.example.blinkit.utils.Utility
import com.example.blinkit.adapter.AdapterSkeleton
import com.example.blinkit.adapter.ProductAdapter
import com.example.blinkit.databinding.FragmentCategoryBinding
import com.example.blinkit.databinding.ProuctItemViewBinding
import com.example.blinkit.model.Product
import com.example.blinkit.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

class CategoryFragment : Fragment() {
    private lateinit var binding : FragmentCategoryBinding
    private val viewModel : HomeViewModel by activityViewModels()
    private lateinit var productAdapter : ProductAdapter
//    private var cartListener : CartListener? = null

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

    private fun onAddButtonClick(product: Product, binding: ProuctItemViewBinding) {
        binding.apply {
            btnAdd.visibility = View.GONE
            btnQuantity.visibility = View.VISIBLE
            onIncrementButtonClick(product, binding)
        }
    }

    private fun onIncrementButtonClick(product: Product, binding: ProuctItemViewBinding) {
        var currentItemCount = binding.tvProductQuantity.text.toString().toIntOrNull() ?: 0
        product.itemCount = ++currentItemCount
        binding.tvProductQuantity.text = currentItemCount.toString()
        viewModel.addProductToCart(product)
        viewModel.incrementCartItemCount()
    }

    private fun onDecrementButtonClick(product: Product, binding: ProuctItemViewBinding) {
        var currentItemCount = binding.tvProductQuantity.text.toString().toInt()
        if(currentItemCount == 1) {
            binding.apply {
                btnAdd.visibility = View.VISIBLE
                btnQuantity.visibility = View.GONE
            }
        }
        product.itemCount = --currentItemCount
        binding.tvProductQuantity.text = currentItemCount.toString()
        viewModel.removeProductFromCart(product)
        viewModel.decrementCartItemCount()
    }

    private fun setProductRecyclerView(category : String) {
        productAdapter = ProductAdapter(this, ::onAddButtonClick, ::onIncrementButtonClick, ::onDecrementButtonClick)

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

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//
//        if(context is CartListener) {
//            cartListener = context
//        } else {
//            throw ClassCastException("$context must implement CartListener")
//        }
//    }

}