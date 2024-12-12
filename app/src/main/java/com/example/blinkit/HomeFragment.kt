package com.example.blinkit

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.blinkit.databinding.FragmentHomeBinding
import com.example.blinkit.databinding.FragmentSplashBinding

class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        Utility.setStatusBarColor(requireActivity(), requireContext(), R.color.home_yellow)

        onSearchTextListener()
        onSearchCrossClick()

        return binding.root
    }

    private fun onSearchCrossClick() {
        binding.crossBtn.setOnClickListener {
            binding.etSearchBar.setText("")
        }
    }

    private fun onSearchTextListener() {
        binding.etSearchBar.addTextChangedListener( object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString().isNotEmpty()) {
                    binding.crossBtn.visibility = View.VISIBLE
                } else {
                    binding.crossBtn.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {}

        })
    }

}