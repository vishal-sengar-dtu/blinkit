package com.example.blinkit.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.blinkit.R
import com.example.blinkit.Utility
import com.example.blinkit.databinding.FragmentOtpBinding
import com.example.blinkit.viewmodel.AuthViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.sign

class OtpFragment : Fragment() {
    private val viewModel : AuthViewModel by viewModels()
    private lateinit var binding : FragmentOtpBinding
    private lateinit var otpFields : Array<EditText>
    private lateinit var phoneNumber : String
    private val countryCode = "+91"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOtpBinding.inflate(layoutInflater)
        otpFields = arrayOf(binding.etOtp1, binding.etOtp2, binding.etOtp3, binding.etOtp4, binding.etOtp5, binding.etOtp6)

        Utility.setStatusBarColor(requireActivity(), requireContext(), R.color.otp_toolbar_bg)
        setOtpTextChangeFocusListener()
        handleBackButton()
        getPhoneNumber()
        sendOtp()
        onLoginClick()

        return binding.root
    }

    private fun onLoginClick() {
        binding.btnLogin.setOnClickListener {
            val otp : String = otpFields.joinToString("") { it.text.toString() }
            binding.tvErrorMsg.visibility = View.GONE

            if(otp.length < otpFields.size) {
                binding.tvErrorMsg.text = requireContext().getString(R.string.enter_a_valid_otp)
                binding.tvErrorMsg.visibility = View.VISIBLE
            } else {
                binding.loaderAnimationView.visibility = View.VISIBLE
                binding.loaderAnimationView.playAnimation()

                otpFields.forEach { it.text?.clear(); it.clearFocus() }
                verifyOtp(otp)
            }
        }
    }

    private fun verifyOtp(otp: String) {
        viewModel.signInWithPhoneAuthCredential(otp)
        observeIsLoginSuccessful()
    }

    private fun observeIsLoginSuccessful() {
        viewModel.isLoginSuccessful.observe(requireActivity()) {
            binding.loaderAnimationView.visibility = View.GONE
            it?.apply {
                if(it) {
                    showLoginAnimationDialog()
                } else {
                    binding.tvErrorMsg.text = requireContext().getString(R.string.incorrect_otp)
                    binding.tvErrorMsg.visibility = View.VISIBLE
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun showLoginAnimationDialog() {
        binding.dialogBox.popUpDialog.visibility = View.VISIBLE
        binding.dialogBox.tickAnimationView.playAnimation()
        binding.nonClickableOverlay.visibility = View.VISIBLE
        binding.nonClickableOverlay.setOnTouchListener { _, _ ->
            true // Consume all touch events
        }
    }

    private fun sendOtp() {
        viewModel.apply {
            sendOtp(phoneNumber, requireActivity())
            otpSent.observe(requireActivity()) {
                if(it) Utility.showToast(requireContext(), "OTP Sent")
            }
        }
    }

    private fun getPhoneNumber() {
        phoneNumber = arguments?.getString("MOBILE_NUMBER").toString()
        phoneNumber = countryCode + phoneNumber
        binding.tvMobileNumber.text = phoneNumber
    }

    private fun handleBackButton() {
        binding.materialToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setOtpTextChangeFocusListener() {
        viewLifecycleOwner.lifecycleScope.launch {
            delay(500) // Small delay to ensure view is fully rendered
            Utility.showKeyboard(binding.etOtp1)
        }

        otpFields.forEachIndexed { index, editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    binding.tvErrorMsg.visibility = View.GONE
                }

                override fun afterTextChanged(s: Editable?) {
                    if(s?.length == 1 && index < otpFields.size - 1) {
                        otpFields[index + 1].requestFocus()
                    } else if(s?.isEmpty() == true && index > 0) {
                        otpFields[index - 1].requestFocus()
                    }
                }

            })
        }
    }

}