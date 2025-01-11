package com.example.blinkit.activity

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.blinkit.CartListener
import com.example.blinkit.Direction
import com.example.blinkit.R
import com.example.blinkit.SharedPreference
import com.example.blinkit.databinding.ActivityHomeBinding
import com.example.blinkit.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity(), CartListener {
    private lateinit var binding : ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var sharedPref : SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        sharedPref = SharedPreference.getInstance(applicationContext)
        setContentView(binding.root)

        val cartItemCount = sharedPref.getCartItemCount()
        viewModel.setCartItemCount(cartItemCount)

        viewModel.cartItemCount.observe(this) { count ->
            updateCartUI(count)
        }
    }

    override fun onStart() {
        super.onStart()
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