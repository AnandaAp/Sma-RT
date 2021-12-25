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
import com.maluku.sma_rt.model.aduan.GetAllAduanItem

class LaporanAdapter(val listLaporan: ArrayList<GetAllAduanItem>): RecyclerView.Adapter<LaporanAdapter.ViewHolder>() {
    fun setData(data : List<GetAllAduanItem>){
        listLaporan.clear()
        listLaporan.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_laporan, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listLaporan[position]
        holder.judulLaporan.text = data.judul.toString()
        holder.keteranganLaporan.text = data.deskripsi.toString()
        Glide.with(holder.itemView)
            .load(data.gambar)
            .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
            .into(holder.gambar)
    }

    override fun getItemCount(): Int {
        return listLaporan.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var judulLaporan: TextView = itemView.findViewById(R.id.tvJudulLaporan)
        var keteranganLaporan: TextView = itemView.findViewById(R.id.tvKetLaporan)
        var gambar: ImageView = itemView.findViewById(R.id.ivLaporan)
    }
}