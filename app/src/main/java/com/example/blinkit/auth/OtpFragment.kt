package com.example.blinkit.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.blinkit.R
import com.example.blinkit.Utility
import com.example.blinkit.databinding.FragmentOtpBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OtpFragment : Fragment() {
    private lateinit var binding : FragmentOtpBinding
    private lateinit var otpFields : Array<EditText>
    private lateinit var phoneNumber : String


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

        return binding.root
    }

    private fun sendOtp() {
        
    }

    private fun getPhoneNumber() {
        phoneNumber = arguments?.getString("MOBILE_NUMBER").toString()
        binding.tvMobileNumber.text = "+91 $phoneNumber"
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

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

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