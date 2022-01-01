package com.maluku.sma_rt.view.warga.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.view.warga.RiwayatPesananUserDirections

class AdapterChildProduk(private val children: List<ModelChildProduk>) :
    RecyclerView.Adapter<AdapterChildProduk.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =  LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_child_produk,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return children.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val child = children[position]
        holder.imageProdukChild.setImageResource(child.imageproduk)
        holder.namaProdukChild.text = child.namaproduk.toString()

        holder.itemView.setOnClickListener { view ->
            val direction = RiwayatPesananUserDirections
                .actionRiwayatPesananUserToDetailPesananUser(

                )
            view.findNavController().navigate(direction)
        }
    }


    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var imageProdukChild: ImageView = itemView.findViewById(R.id.imageprodukchild)
        var namaProdukChild: TextView = itemView.findViewById(R.id.namaprodukchild)


    }
}

