package com.maluku.sma_rt.view.warga

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentAkunWargaBinding
import com.maluku.sma_rt.databinding.FragmentJualbeliWargaBinding
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewInfo
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewTerlaris
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewToko

class JualbeliWarga : Fragment() {

    private lateinit var binding: FragmentJualbeliWargaBinding

    private lateinit var rvToko: RecyclerView
    private lateinit var rvTerlaris: RecyclerView
    private lateinit var adapterToko: RecyclerViewToko
    private lateinit var adapterTerlaris: RecyclerViewTerlaris


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentJualbeliWargaBinding.inflate(layoutInflater)


        rvToko = binding.rvToko
        adapterToko = RecyclerViewToko()

        rvTerlaris = binding.rvTerlaris
        adapterTerlaris = RecyclerViewTerlaris()

        //Horizontal RecyclerView
        rvToko.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        rvToko.setAdapter(adapterToko)

        //Vertical Recyclerview
        rvTerlaris.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        rvTerlaris.setAdapter(adapterTerlaris)







        return binding.root
    }

}