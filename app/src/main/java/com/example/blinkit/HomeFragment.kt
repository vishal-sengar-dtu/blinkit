package com.example.blinkit

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.blinkit.adapter.AdapterCategory
import com.example.blinkit.adapter.AdapterSkeleton
import com.example.blinkit.databinding.FragmentHomeBinding
import com.example.blinkit.model.Category
import com.example.blinkit.viewmodel.HomeViewModel


class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private val viewModel : HomeViewModel by viewModels()
    private lateinit var groceryAdapter : AdapterCategory
    private lateinit var snacksAdapter : AdapterCategory
    private lateinit var beautyAdapter : AdapterCategory
    private lateinit var householdAdapter : AdapterCategory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        Utility.setStatusAndNavigationBarColor(requireActivity(), requireContext(), R.color.home_yellow, R.color.white)

        onSearchTextListener()
        onSearchCrossClick()
        showSkeletonLoader()
        setCategoriesRecyclerView()

        return binding.root
    }

    private fun setCategoriesRecyclerView() {
        groceryAdapter = AdapterCategory(Constants.groceryCategoryList)
        snacksAdapter = AdapterCategory(Constants.snacksCategoryList)
        beautyAdapter = AdapterCategory(Constants.beautyCategoryList)
        householdAdapter = AdapterCategory(Constants.householdCategoryList)

        binding.apply {
            rvGrocery.adapter = groceryAdapter
            rvSnacks.adapter = snacksAdapter
            rvBeauty.adapter = beautyAdapter
            rvHousehold.adapter = householdAdapter
        }

        hideSkeletonLoader()

    }

    private fun showSkeletonLoader() {
        binding.apply {
            scrollView.visibility = View.GONE

            skeletonScrollView.visibility = View.VISIBLE
            shimmerBestSeller.showShimmer(true)
            shimmer1.showShimmer(true)
            shimmer2.showShimmer(true)
            shimmer3.showShimmer(true)
            shimmer4.showShimmer(true)

            val skeletonList = List(8) { Category(null, null) }

            binding.apply {
                rvGrocerySkeleton.adapter = AdapterSkeleton(skeletonList)
                rvSnacksSkeleton.adapter = AdapterSkeleton(skeletonList)
                rvBeautySkeleton.adapter = AdapterSkeleton(skeletonList)
                rvHouseholdSkeleton.adapter = AdapterSkeleton(skeletonList)
            }
        }
    }

    private fun hideSkeletonLoader() {
        binding.apply {
            skeletonScrollView.visibility = View.GONE
            shimmerBestSeller.showShimmer(false)
            shimmer1.showShimmer(false)
            shimmer2.showShimmer(false)
            shimmer3.showShimmer(false)
            shimmer4.showShimmer(false)

            scrollView.visibility = View.VISIBLE
        }
    }

    private fun onSearchCrossClick() {
        binding.crossBtn.setOnClickListener {
            binding.etSearchBar.setText("")
        }
    }

    private fun onSearchTextListener() {
        binding.etSearchBar.addTextChangedListener( object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString().isNotEmpty()) {
                    binding.crossBtn.visibility = View.VISIBLE
                } else {
                    binding.crossBtn.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {}

        })
    }

}