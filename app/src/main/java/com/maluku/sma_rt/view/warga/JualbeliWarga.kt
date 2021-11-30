package com.maluku.sma_rt.view.warga

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewInfo
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewTerlaris
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewToko

class JualbeliWarga : Fragment() {

    private lateinit var rvToko: RecyclerView
    private lateinit var rvTerlaris: RecyclerView
    private lateinit var adapterToko: RecyclerViewToko
    private lateinit var adapterTerlaris: RecyclerViewTerlaris




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_jualbeli_warga, container, false)


        rvToko = view.findViewById(R.id.rv_toko)
        adapterToko = RecyclerViewToko()

        rvTerlaris = view.findViewById(R.id.rv_terlaris)
        adapterTerlaris = RecyclerViewTerlaris()

        //Horizontal RecyclerView
        rvToko.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        rvToko.setAdapter(adapterToko)


        rvTerlaris.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        rvTerlaris.setAdapter(adapterTerlaris)

        val buttonTroli = view.findViewById<ImageView>(R.id.troli)
        buttonTroli.setOnClickListener {
            findNavController().navigate(R.id.action_jualbeliWarga_to_pesananUserPage)
        }






        return view
    }

}