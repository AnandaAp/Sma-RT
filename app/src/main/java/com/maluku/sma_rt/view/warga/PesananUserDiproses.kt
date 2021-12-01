package com.maluku.sma_rt.view.warga

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentPesananUserDiprosesBinding


class PesananUserDiproses : Fragment() {

    private lateinit var binding: FragmentPesananUserDiprosesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPesananUserDiprosesBinding.inflate(layoutInflater)


        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_pesananUserDiproses_to_produkPage)
        }

        binding.btnPesanandiproses.setOnClickListener {
            findNavController().navigate(R.id.action_pesananUserDiproses_to_pesananUserSudahsiap)
        }


        return binding.root
    }

}