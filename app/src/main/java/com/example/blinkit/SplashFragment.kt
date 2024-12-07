package com.example.blinkit

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.blinkit.databinding.FragmentLoginBinding
import com.example.blinkit.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    private lateinit var binding : FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(layoutInflater)

        Utility.setStatusBarColor(requireActivity(), requireContext(), R.color.splash_yellow)

        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_loginFragment2)
        }, 3000)

        return binding.root
    }

}