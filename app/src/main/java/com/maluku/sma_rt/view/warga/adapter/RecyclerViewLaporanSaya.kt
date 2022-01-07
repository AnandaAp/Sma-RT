package com.maluku.sma_rt.view.warga.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.view.warga.InformasiWargaDirections
import com.maluku.sma_rt.view.warga.LaporanPageDirections

class RecyclerViewLaporanSaya: RecyclerView.Adapter<RecyclerViewLaporanSaya.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_laporansaya, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.setOnClickListener { view ->
            val direction = LaporanPageDirections
                .actionLaporanPageToDetailLaporanSaya()
            view.findNavController().navigate(direction)
        }

    }

    override fun getItemCount(): Int {
        return 10
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
}