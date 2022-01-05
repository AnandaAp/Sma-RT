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
import com.maluku.sma_rt.databinding.FragmentLaporanListBinding
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewLaporanList




class LaporanList : Fragment() {

    private lateinit var binding: FragmentLaporanListBinding
    private lateinit var rvLaporanList: RecyclerView
    private lateinit var adapterLaporanList: RecyclerViewLaporanList

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabAddLaporan()
        setRecyclerViewLaporanList()
    }

    private fun setRecyclerViewLaporanList() {
        rvLaporanList = binding.rvLaporanList
        rvLaporanList.setHasFixedSize(true)
        rvLaporanList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        adapterLaporanList = RecyclerViewLaporanList()
        rvLaporanList.adapter = adapterLaporanList
    }

    private fun fabAddLaporan() {
        binding.addLaporan.setOnClickListener {
            findNavController().navigate(R.id.action_laporanPage_to_laporanWarga)
        }
    }




    private fun bindingView(): View {
        binding = FragmentLaporanListBinding.inflate(layoutInflater)
        return binding.root

    }
}