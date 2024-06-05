package com.example.blinkituserapp.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.blinkituserapp.R
import com.example.blinkituserapp.Utils
import com.example.blinkituserapp.databinding.FragmentOTPBinding
import com.example.blinkituserapp.model.Users
import com.example.blinkituserapp.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

class OTPFragment : Fragment() {

    private val viewModel : AuthViewModel by viewModels()
    private lateinit var binding : FragmentOTPBinding
    private lateinit var otpEditTexts : Array<EditText>
    private lateinit var phoneNumber : String
    private val countryCode : String = "+91"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentOTPBinding.inflate(inflater, container, false)
        otpEditTexts = arrayOf(binding.etOtp1, binding.etOtp2, binding.etOtp3, binding.etOtp4, binding.etOtp5, binding.etOtp6)

        setupMobileNumber()
        onBackButtonClick()
        setupOtpTextAndFocusListeners()
        sendOtp()
        onLoginButtonClick()

        return binding.root
    }

    private fun onLoginButtonClick() {
        binding.btnLogin.setOnClickListener {
            Utils.showDialog(requireContext(), "Signing you ...")
            val otp = otpEditTexts.joinToString("") {
                it.text.toString()
            }

            if(otp.length < otpEditTexts.size) {
                Utils.showToast(requireContext(), "Please enter correct OTP")
            } else {
                otpEditTexts.forEach {
                    it.text.clear()
                    it.clearFocus()
                }
                verifyOTP(otp)
            }
        }
    }

    private fun verifyOTP(otp : String) {
        val user = Users(
            uId = Utils.getCurrentUserId(),
            userPhoneNumber = phoneNumber,
            address = null
        )

        viewModel.signInWithPhoneAuthCredential(otp, user)
        lifecycleScope.launch {
            viewModel.loginSuccessful.collect() {
                if(it) {
                    Utils.hideDialog()
                    Utils.showToast(requireContext(), "Login Successful")
                }
            }
        }
    }

    private fun sendOtp() {
        Utils.showDialog(requireContext(), "Sending OTP ...")
        viewModel.apply {
            sendOtp(phoneNumber, requireActivity())
            lifecycleScope.launch {
                otpSent.collect {
                    if(it) {
                        Utils.hideDialog()
                        Utils.showToast(requireContext(), "We have a sent a verification code to you via SMS")
                    }
                }
            }
        }
    }

    private fun setupOtpTextAndFocusListeners() {
        otpEditTexts[0].requestFocus()

        for(i in otpEditTexts.indices) {
            otpEditTexts[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.length == 1) {
                        if (i < otpEditTexts.size - 1) {
                            otpEditTexts[i + 1].requestFocus()
                        }
                    } else if (s?.length == 0) {
                        if (i > 0) {
                            otpEditTexts[i - 1].requestFocus()
                        }
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })
        }
    }

    private fun onBackButtonClick() {
        binding.materialToolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_OTPFragment_to_signinFragment)
        }
    }

    private fun setupMobileNumber() {
        val bundle = arguments
        phoneNumber = bundle?.getString("MOBILE_NUMBER").toString()
        phoneNumber = "$countryCode$phoneNumber"
        binding.tvMobileNumber.text = "$countryCode $phoneNumber"
    }
}