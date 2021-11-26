package com.maluku.sma_rt.view.warga

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentProdukWargaBinding
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewProdukwarga
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewToko

class ProdukWarga : Fragment() {

    private lateinit var binding: FragmentProdukWargaBinding

    private lateinit var rvProduk: RecyclerView
    private lateinit var adapterProduk: RecyclerViewProdukwarga

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProdukWargaBinding.inflate(layoutInflater)

        rvProduk = binding.rvProdukwarga
        adapterProduk = RecyclerViewProdukwarga()

        rvProduk.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        rvProduk.setAdapter(adapterProduk)

        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.action_produkWarga_to_kelolaToko)
        }

        binding.btnTambahproduk.setOnClickListener{
            findNavController().navigate(R.id.action_produkWarga_to_tambahProduk)
        }



        return binding.root
    }

}