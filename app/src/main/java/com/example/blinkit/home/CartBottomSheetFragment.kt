package com.example.blinkit.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.blinkit.R
import com.example.blinkit.databinding.FragmentCartBottomSheetBinding
import com.example.blinkit.roomdb.CartProduct
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CartBottomSheetFragment : Fragment(){
    private lateinit var binding : FragmentCartBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }
}