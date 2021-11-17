package com.maluku.sma_rt.view.warga

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentLoginWargaBinding

class LoginWarga : Fragment() {

    private lateinit var binding: FragmentLoginWargaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginWargaBinding.inflate(layoutInflater)

        binding.btnDaftar.setOnClickListener{
            findNavController().navigate(R.id.action_loginWarga_to_registerWarga)
        }
        return binding.root
    }
}