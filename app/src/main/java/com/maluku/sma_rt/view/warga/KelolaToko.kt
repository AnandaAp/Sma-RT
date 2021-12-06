package com.maluku.sma_rt.view.warga

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentKelolaTokoBinding


class KelolaToko : Fragment() {

    private lateinit var binding: FragmentKelolaTokoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentKelolaTokoBinding.inflate(layoutInflater)

        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.action_kelolaToko_to_akunWarga)
        }

        binding.cardProduk.setOnClickListener{
            findNavController().navigate(R.id.action_kelolaToko_to_produkPage)
        }

        binding.cardPesanan.setOnClickListener {
            findNavController().navigate(R.id.action_kelolaToko_to_pesananPenjualPage)
        }



        return binding.root
    }


}