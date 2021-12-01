package com.maluku.sma_rt.view.warga

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentPesananUserMenungguBinding


class PesananUserMenunggu : Fragment() {

    private lateinit var binding: FragmentPesananUserMenungguBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPesananUserMenungguBinding.inflate(layoutInflater)


        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_pesananUserMenunggu_to_produkPage)
        }

        binding.btnPesananmenunggu.setOnClickListener {
            findNavController().navigate(R.id.action_pesananUserMenunggu_to_pesananUserDiproses)
        }

        return binding.root
    }

}