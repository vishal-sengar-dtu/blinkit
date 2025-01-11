package com.example.blinkit.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.blinkit.utils.Constants
import com.example.blinkit.R
import com.example.blinkit.utils.Utility
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

        Utility.setStatusAndNavigationBarColor(
            requireActivity(),
            requireContext(),
            R.color.home_yellow,
            R.color.white
        )

        showSkeletonLoader()
        setCategoriesRecyclerView()
        onSearchClick()

        return binding.root
    }

    private fun onSearchClick() {
        binding.customEditText.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun onCategoryClick(category : Category) {
        val bundle = Bundle().apply {
            putString("CATEGORY_NAME", category.title)
        }
        findNavController().navigate(R.id.action_homeFragment_to_categoryFragment, bundle)
    }

    private fun setCategoriesRecyclerView() {
        groceryAdapter = AdapterCategory(Constants.groceryCategoryList, ::onCategoryClick)
        snacksAdapter = AdapterCategory(Constants.snacksCategoryList, ::onCategoryClick)
        beautyAdapter = AdapterCategory(Constants.beautyCategoryList, ::onCategoryClick)
        householdAdapter = AdapterCategory(Constants.householdCategoryList, ::onCategoryClick)

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

            val skeletonList = List(8) { "" }

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

}