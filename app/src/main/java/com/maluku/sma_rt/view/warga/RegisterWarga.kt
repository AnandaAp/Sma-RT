package com.maluku.sma_rt.view.warga

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.navigation.fragment.findNavController
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentRegisterWargaBinding

class RegisterWarga : Fragment() {

    private lateinit var binding: FragmentRegisterWargaBinding
    private lateinit var autoCompleteTextView: AutoCompleteTextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterWargaBinding.inflate(layoutInflater)

        val jenisKelamin = resources.getStringArray(R.array.jenis_kelamin)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, jenisKelamin)


        autoCompleteTextView = binding.tvAutocomplete
        autoCompleteTextView.setAdapter(arrayAdapter)

        binding.btnLogin.setOnClickListener{
            findNavController().navigate(R.id.action_registerWarga_to_loginWarga)
        }

        binding.btnDaftar.setOnClickListener{
            findNavController().navigate(R.id.action_registerWarga_to_viewPagerFragment)
        }

        return binding.root
    }
}