package com.example.blinkit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.blinkit.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding : FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        Utility.setStatusBarColor(requireActivity(), requireContext(), R.color.login_light)
        return binding.root
    }

}