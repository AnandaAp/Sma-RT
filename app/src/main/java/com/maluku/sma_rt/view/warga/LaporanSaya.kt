package com.maluku.sma_rt.view.warga

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentKelolaTokoBinding
import com.maluku.sma_rt.databinding.FragmentLaporanSayaBinding
import com.maluku.sma_rt.presenter.WargaKelolaTokoPresenter
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewLaporanSaya
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewProdukpage


class LaporanSaya : Fragment() {

    private lateinit var binding: FragmentLaporanSayaBinding
    private lateinit var rvLaporanSaya: RecyclerView
    private lateinit var adapterLaporanSaya: RecyclerViewLaporanSaya

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewLaporanSaya()
    }

    private fun setRecyclerViewLaporanSaya() {
        rvLaporanSaya = binding.rvLaporanSaya
        rvLaporanSaya.setHasFixedSize(true)
        rvLaporanSaya.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        adapterLaporanSaya = RecyclerViewLaporanSaya()
        rvLaporanSaya.adapter = adapterLaporanSaya
    }


    private fun bindingView(): View {
        binding = FragmentLaporanSayaBinding.inflate(layoutInflater)
        return binding.root

    }
}