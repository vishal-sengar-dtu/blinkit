package com.example.blinkit

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.blinkit.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding : FragmentLoginBinding
    private val maxLength = 10

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)

        Utility.setStatusBarColor(requireActivity(), requireContext(), R.color.login_light)
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
                findNavController().navigate(R.id.action_loginFragment2_to_otpFragment, bundle)
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
                    binding.btnContinue.setBackgroundDrawable(
                        ContextCompat.getDrawable(requireContext(),
                        R.drawable.round_button_green
                    ))
                } else {
                    binding.btnContinue.setBackgroundDrawable(
                        ContextCompat.getDrawable(requireContext(),
                        R.drawable.round_button_grey
                    ))
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

}