package com.maluku.sma_rt.view.warga

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.maluku.sma_rt.R



import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewGaleri
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewInfo





class HomeWarga : Fragment() {

    private lateinit var rvInfo: RecyclerView
    private lateinit var rvGaleri: RecyclerView
    private lateinit var adapterInfo: RecyclerViewInfo
    private lateinit var adapterGaleri: RecyclerViewGaleri


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home_warga, container, false)


        rvInfo = view.findViewById(R.id.rv_info)
        adapterInfo = RecyclerViewInfo()

        rvGaleri = view.findViewById(R.id.rv_galeri)
        adapterGaleri = RecyclerViewGaleri()


        //Horizontal RecyclerView
        rvInfo.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        rvInfo.setAdapter(adapterInfo)

        rvGaleri.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        rvGaleri.setAdapter(adapterGaleri)


        val menuLaporan = view.findViewById<ImageView>(R.id.btn_laporan)
        menuLaporan.setOnClickListener {
            findNavController().navigate(R.id.action_homeWarga_to_laporanWarga)
        }

        return view
    }

}