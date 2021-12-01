package com.maluku.sma_rt.view.pengurus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.maluku.sma_rt.R
import com.maluku.sma_rt.model.warga.GetAllWargaItem

class WargaAdapter(val listWarga: ArrayList<GetAllWargaItem>): RecyclerView.Adapter<WargaAdapter.ViewHolder>()  {
    fun setData(data : List<GetAllWargaItem>){
        listWarga.clear()
        listWarga.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_warga, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listWarga[position]
        holder.nama.text = data.nama.toString()
        holder.noHp.text = data.noHp.toString()
        holder.email.text = data.email.toString()
        holder.gender.text = data.gender.toString()
        Glide.with(holder.itemView)
            .load(data.gambar)
            .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
            .into(holder.gambar)
    }

    override fun getItemCount(): Int {
        return listWarga.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var nama: TextView = itemView.findViewById(R.id.tv_nama)
        var noHp: TextView = itemView.findViewById(R.id.tv_noHp)
        var email: TextView = itemView.findViewById(R.id.tv_email)
        var gender: TextView = itemView.findViewById(R.id.tv_gender)
        var gambar: ImageView = itemView.findViewById(R.id.ivProfilWarga)
    }
}