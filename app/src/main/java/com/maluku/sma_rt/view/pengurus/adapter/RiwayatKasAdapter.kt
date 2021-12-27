package com.maluku.sma_rt.view.pengurus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.model.tagihan.GetAllTagihanItem

class RiwayatKasAdapter(val listTagihan: ArrayList<GetAllTagihanItem>): RecyclerView.Adapter<RiwayatKasAdapter.ViewHolder>() {
    fun setData(data : List<GetAllTagihanItem>){
        listTagihan.clear()
        listTagihan.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_riwayat_kas, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listTagihan[position]
        holder.namaTagihan.text = data.nama.toString()
        holder.ketTagihan.text = data.detail.toString()
        holder.totalTagihan.text = data.jumlah.toString()
    }

    override fun getItemCount(): Int {
        return listTagihan.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var namaTagihan: TextView = itemView.findViewById(R.id.tvNamaTagihan)
        var ketTagihan: TextView = itemView.findViewById(R.id.tvKetTagihan)
        var totalTagihan: TextView = itemView.findViewById(R.id.tvTotalTagihan)
    }

}