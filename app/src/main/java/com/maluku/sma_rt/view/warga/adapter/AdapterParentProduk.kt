package com.maluku.sma_rt.view.warga.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.view.warga.ProdukPageDirections
import com.maluku.sma_rt.view.warga.RiwayatPesananUser
import com.maluku.sma_rt.view.warga.RiwayatPesananUserDirections

class AdapterParentProduk(val parents: List<ModelParentProduk>) :
    RecyclerView.Adapter<AdapterParentProduk.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_parent_produk,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val parent = parents[position]
        holder.namaToko.text = parent.namatoko.toString()
        holder.recyclerView.apply {
            layoutManager = LinearLayoutManager(holder.recyclerView.context, LinearLayoutManager.VERTICAL, false)
            adapter = AdapterChildProduk(parent.children)
        }





    }

    override fun getItemCount(): Int {
       return parents.size
    }

    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var recyclerView: RecyclerView = itemView.findViewById(R.id.rv_child)
        var namaToko: TextView = itemView.findViewById(R.id.namatoko)
    }
}


