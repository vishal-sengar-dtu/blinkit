package com.example.blinkituserapp.auth

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.blinkituserapp.R
import com.example.blinkituserapp.Utils
import com.example.blinkituserapp.databinding.FragmentSigninBinding

class SigninFragment : Fragment() {

    private lateinit var binding: FragmentSigninBinding
    private val maxLength = 10

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSigninBinding.inflate(inflater, container, false)

        setStatusBarColor()
        setMobileNumberListener()
        onContinueButtonClick()

        return binding.root
    }

    private fun onContinueButtonClick() {
        binding.btnContinue.setOnClickListener {
            val mobileNumber = binding.etMobileNumber.text.toString()

            if(mobileNumber.length < maxLength) {
                binding.tvErrorMsg.visibility = View.VISIBLE
            } else {
                binding.tvErrorMsg.visibility = View.GONE

                // passing mobile number to OTP fragment
                val bundle = Bundle()
                bundle.putString("MOBILE_NUMBER", mobileNumber)
                findNavController().navigate(R.id.action_signinFragment_to_OTPFragment, bundle)
            }
        }
    }

    private fun setMobileNumberListener() {

        // Setting text change listener on mobile number edit text
        binding.etMobileNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Hiding error message on text change
                binding.tvErrorMsg.visibility = View.GONE

                // Changing button background color based on mobile number length
                if(s.toString().length == maxLength) {
                    binding.btnContinue.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(),
                        R.drawable.round_button_green
                    ))
                } else {
                    binding.btnContinue.setBackgroundColor(ContextCompat.getColor(requireContext(),
                        R.color.blinkit_grey
                    ))
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun setStatusBarColor() {
        activity?.window?.apply {
            val statusBarColors = ContextCompat.getColor(requireContext(), com.google.android.material.R.color.mtrl_btn_transparent_bg_color)
            statusBarColor = statusBarColors

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }

}