package com.example.blinkit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.blinkit.databinding.FragmentLoginBinding
import com.example.blinkit.databinding.FragmentOtpBinding

class OtpFragment : Fragment() {
    private lateinit var binding : FragmentOtpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOtpBinding.inflate(layoutInflater)
        Utility.setStatusBarColor(requireActivity(), requireContext(), R.color.otp_toolbar_bg)
        return binding.root
    }

}