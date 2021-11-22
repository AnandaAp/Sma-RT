package com.maluku.sma_rt.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentLoginChoiceBinding

class LoginChoice : Fragment() {

    private lateinit var binding: FragmentLoginChoiceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginChoiceBinding.inflate(layoutInflater)

        binding.btnLoginwarga.setOnClickListener{
            findNavController().navigate(R.id.action_loginChoice_to_loginWarga)
        }
        binding.btnLoginrt.setOnClickListener {
            findNavController().navigate(R.id.action_loginChoice_to_loginRT)
        }
        return binding.root


    }
}