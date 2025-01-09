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
import com.example.blinkit.R
import com.example.blinkit.databinding.ActivityHomeBinding
import com.example.blinkit.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity(), CartListener {
    private lateinit var binding : ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        setContentView(binding.root)

        viewModel.cartItemCount.observe(this) { count ->
            updateCartUI(count)
        }
    }

    override fun updateCartUI(itemCount : Int) {
        binding.tvCount.text = itemCount.toString()
        binding.llCartBottomView.visibility = if(itemCount > 0) View.VISIBLE else View.GONE
    }

    override fun cartAnimation(isPopUp: Boolean) {
        val popUp = AnimationUtils.loadAnimation(baseContext, R.anim.pop_up_from_bottom)
        val popDown = AnimationUtils.loadAnimation(baseContext, R.anim.pop_up_from_bottom)
        if(isPopUp) {
            binding.llCartBottomView.startAnimation(popUp)
        } else {
            binding.llCartBottomView.startAnimation(popDown)
        }
    }


}