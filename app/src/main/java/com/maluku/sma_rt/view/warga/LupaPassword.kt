package com.maluku.sma_rt.view.warga

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentLoginWargaBinding
import com.maluku.sma_rt.databinding.FragmentLupaPasswordBinding


class LupaPassword : Fragment() {

    private lateinit var binding: FragmentLupaPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnBack()
        btnSubmitEmailVerifikasi()

    }

    private fun bindingView(): View {
        binding = FragmentLupaPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun btnBack() {
        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.action_lupaPassword_to_loginWarga)
        }
    }

    private fun btnSubmitEmailVerifikasi() {
        binding.btnSubmit.setOnClickListener{
            findNavController().navigate(R.id.action_lupaPassword_to_resetPassword)
        }
    }
}
