package com.maluku.sma_rt.view.warga

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentPesananUserSudahsiapBinding


class PesananUserSudahsiap : Fragment() {

    private lateinit var binding: FragmentPesananUserSudahsiapBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPesananUserSudahsiapBinding.inflate(layoutInflater)

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_pesananUserSudahsiap_to_produkPage)
        }

        binding.cardPesananuserselesai.setOnClickListener {
            findNavController().navigate(R.id.action_pesananUserSudahsiap_to_pesananuserSelesaiDetails)
        }

        return binding.root
    }
}
