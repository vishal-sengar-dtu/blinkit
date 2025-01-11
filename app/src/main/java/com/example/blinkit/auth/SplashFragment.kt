package com.example.blinkit.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.blinkit.BringItApplication
import com.example.blinkit.R
import com.example.blinkit.SharedPreference
import com.example.blinkit.utils.Utility
import com.example.blinkit.activity.HomeActivity
import com.example.blinkit.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    private lateinit var binding : FragmentSplashBinding
    private lateinit var sharedPref : SharedPreference

    companion object {
        private const val SPLASH_DELAY = 1800L
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(layoutInflater)
        sharedPref = (requireActivity().application as BringItApplication).sharedPreference

        Utility.setStatusAndNavigationBarColor(requireActivity(), requireContext(), R.color.splash_yellow, R.color.splash_yellow)
        Handler(Looper.getMainLooper()).postDelayed({
            if(sharedPref.isUserLoggedIn()) {
                val homeIntent = Intent(requireContext(), HomeActivity::class.java)
                startActivity(homeIntent)
                requireActivity().finish()
            } else {
                if(isAdded) findNavController().navigate(R.id.action_splashFragment_to_loginFragment2)
            }
        }, SPLASH_DELAY)

        return binding.root
    }

}