package com.maluku.sma_rt.view.warga.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R

class RecyclerViewGaleri: RecyclerView.Adapter<RecyclerViewGaleri.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_galeriwarga_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 5
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

}