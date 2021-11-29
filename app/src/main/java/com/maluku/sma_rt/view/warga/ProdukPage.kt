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
import com.maluku.sma_rt.databinding.FragmentProdukPageBinding
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewProdukpage

class ProdukPage : Fragment() {

    private lateinit var binding: FragmentProdukPageBinding

    private lateinit var rvProduk: RecyclerView
    private lateinit var adapterProduk: RecyclerViewProdukpage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProdukPageBinding.inflate(layoutInflater)

        rvProduk = binding.rvProdukpage
        adapterProduk = RecyclerViewProdukpage()

        rvProduk.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        rvProduk.setAdapter(adapterProduk)

        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.action_produkPage_to_kelolaToko)
        }

//        binding.btnTambahproduk.setOnClickListener{
//            findNavController().navigate(R.id.action_produkPage_to_tambahProduk)
//        }


        return binding.root
    }

}