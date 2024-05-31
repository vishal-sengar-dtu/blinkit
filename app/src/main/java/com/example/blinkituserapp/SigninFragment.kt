package com.example.blinkituserapp

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.example.blinkituserapp.databinding.FragmentSigninBinding

class SigninFragment : Fragment() {

    private lateinit var binding: FragmentSigninBinding
    private val maxLength = 10

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSigninBinding.inflate(inflater, container, false)
        setStatusBarColor()
        setMobileNumberListener()

        return binding.root
    }

    private fun setMobileNumberListener() {
        binding.etMobileNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString().length == maxLength) {
                    binding.btnContainer.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blinkit_green))
                } else {
                    binding.btnContainer.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blinkit_grey))
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