package com.example.blinkit.activity

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.blinkit.BringItApplication
import com.example.blinkit.CartListener
import com.example.blinkit.Direction
import com.example.blinkit.repository.HomeRepository
import com.example.blinkit.R
import com.example.blinkit.SharedPreference
import com.example.blinkit.databinding.ActivityHomeBinding
import com.example.blinkit.viewmodel.HomeViewModel
import com.example.blinkit.viewmodel.HomeViewModelFactory
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity(), CartListener {
    private lateinit var binding : ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var homeRepository : HomeRepository
    private lateinit var sharedPref : SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        homeRepository = HomeRepository((application as BringItApplication).getCartProductDao())
        viewModel = ViewModelProvider(this, HomeViewModelFactory(homeRepository))[HomeViewModel::class.java]
        sharedPref = (application as BringItApplication).sharedPreference
        setContentView(binding.root)

        handleExistingCartProducts()

        lifecycleScope.launch {
            viewModel.cartItemCount.collect { itemCount ->
                updateCartUI(itemCount)
            }

            viewModel.cartItemList.collect { itemList ->

            }

        }

    }

    private fun handleExistingCartProducts() {
        val cartItemCount = sharedPref.getCartItemCount()
        viewModel.setCartItemCount(cartItemCount)
    }

    override fun updateCartUI(itemCount : Int) {
        binding.tvCount.text = itemCount.toString()
        sharedPref.saveCartItemCount(itemCount)
        binding.llCartBottomView.visibility = if(itemCount > 0) View.VISIBLE else View.GONE
    }

    override fun cartAnimation(direction: Direction) {
        val popUp = AnimationUtils.loadAnimation(baseContext, R.anim.pop_up_from_bottom)
        val popDown = AnimationUtils.loadAnimation(baseContext, R.anim.pop_up_from_bottom)

        when(direction) {
            Direction.UP -> binding.llCartBottomView.startAnimation(popUp)
            Direction.DOWN -> binding.llCartBottomView.startAnimation(popDown)
        }
    }


}