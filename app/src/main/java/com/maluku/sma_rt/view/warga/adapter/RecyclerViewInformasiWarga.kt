package com.maluku.sma_rt.view.warga.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.view.warga.InformasiWargaDirections
import com.maluku.sma_rt.view.warga.RiwayatPesananUserDirections

class RecyclerViewInformasiWarga: RecyclerView.Adapter<RecyclerViewInformasiWarga.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.informasi_warga_recycler_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.itemView.setOnClickListener { view ->
            val direction = InformasiWargaDirections
                .actionInformasiWargaToDetailInformasiBerita()
            view.findNavController().navigate(direction)
        }

    }

    override fun getItemCount(): Int {
        return 20
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

}