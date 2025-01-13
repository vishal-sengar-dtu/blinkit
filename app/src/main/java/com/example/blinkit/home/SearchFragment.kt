package com.example.blinkit.home

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.blinkit.CartListener
import com.example.blinkit.R
import com.example.blinkit.utils.Utility
import com.example.blinkit.adapter.AdapterSkeleton
import com.example.blinkit.adapter.ProductAdapter
import com.example.blinkit.databinding.FragmentSearchBinding
import com.example.blinkit.databinding.ProuctItemViewBinding
import com.example.blinkit.model.Product
import com.example.blinkit.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var productAdapter: ProductAdapter
    private val viewModel : HomeViewModel by activityViewModels()
    private var cartListener : CartListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)

        Utility.setStatusAndNavigationBarColor(
            requireActivity(),
            requireContext(),
            R.color.search_toolbar_bg,
            R.color.white
        )

        requestFocusAndShowKeyboard()
        showSkeletonLoader()
        setProductRecyclerView()
        searchProductFilter()
        if(getContext() is CartListener) {

        }

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

    private fun setProductRecyclerView() {
        productAdapter = ProductAdapter(this, ::onAddButtonClick, ::onIncrementButtonClick, ::onDecrementButtonClick)

        lifecycleScope.launch {
            viewModel.fetchAllProducts().collect {
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
            findNavController().navigate(R.id.action_searchFragment_to_homeFragment)
        }
    }

    private fun onCrossButtonClick() {
        binding.crossBtn.setOnClickListener {
            binding.etSearchBar.setText("")
        }
    }

    private fun requestFocusAndShowKeyboard() {
        binding.etSearchBar.requestFocus()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is CartListener) {
            cartListener = context
        } else {
            throw ClassCastException("$context must implement CartListener")
        }
    }

}