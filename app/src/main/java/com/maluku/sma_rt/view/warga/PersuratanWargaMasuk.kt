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
import com.maluku.sma_rt.databinding.FragmentPersuratanPageBinding
import com.maluku.sma_rt.databinding.FragmentPersuratanWargaMasukBinding
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewLaporanSaya
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewSuratMasuk

class PersuratanWargaMasuk : Fragment() {


    private lateinit var binding: FragmentPersuratanWargaMasukBinding

    private lateinit var rvSuratMasuk: RecyclerView
    private lateinit var adapterSuratMasuk: RecyclerViewSuratMasuk

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = bindingView()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnBack()
        setRecyclerViewSuratMasuk()

    }

    private fun btnBack() {
        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.action_persuratanWargaMasuk_to_persuratanPage)
        }
    }

    private fun setRecyclerViewSuratMasuk() {
        rvSuratMasuk = binding.rvSuratmasuk
        rvSuratMasuk.setHasFixedSize(true)
        rvSuratMasuk.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        adapterSuratMasuk = RecyclerViewSuratMasuk()
        rvSuratMasuk.adapter = adapterSuratMasuk
    }

    private fun bindingView(): View {
        binding = FragmentPersuratanWargaMasukBinding.inflate(layoutInflater)
        return binding.root
    }

}