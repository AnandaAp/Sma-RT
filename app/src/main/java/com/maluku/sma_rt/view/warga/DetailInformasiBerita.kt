package com.maluku.sma_rt.view.warga

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentDetailInformasiBeritaBinding
import com.maluku.sma_rt.databinding.FragmentDetailLaporanListBinding
import com.maluku.sma_rt.databinding.FragmentDetailPesananUserBinding


class DetailInformasiBerita : Fragment() {

    private lateinit var binding: FragmentDetailInformasiBeritaBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = bindingView()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }



    private fun bindingView(): View {
        binding = FragmentDetailInformasiBeritaBinding.inflate(layoutInflater)
        return binding.root
    }

}