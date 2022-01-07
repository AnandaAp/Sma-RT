package com.maluku.sma_rt.view.warga.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.view.warga.LaporanListDirections

class RecyclerViewLaporanList: RecyclerView.Adapter<RecyclerViewLaporanList.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_laporanlist, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.setOnClickListener { view ->
            val direction = LaporanListDirections
                .actionLaporanListToDetailLaporanList()
            view.findNavController().navigate(direction)
        }


    }

    override fun getItemCount(): Int {
        return 10
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
}