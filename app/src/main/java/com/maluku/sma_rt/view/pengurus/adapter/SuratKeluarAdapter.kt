package com.maluku.sma_rt.view.pengurus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.model.persuratan.GetAllPersuratanItem

class SuratKeluarAdapter(val listSuratKeluar: ArrayList<GetAllPersuratanItem>): RecyclerView.Adapter<SuratKeluarAdapter.ViewHolder>() {
    fun setData(data : List<GetAllPersuratanItem>){
        listSuratKeluar.clear()
        listSuratKeluar.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_surat_keluar,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listSuratKeluar[position]
        holder.judulSurat.text = data.judul.toString()
        holder.keperluanSurat.text = data.keperluan.toString()
        holder.statusSurat.text = data.status.toString()
        holder.tanggalSurat.text = data.tanggal.toString()
        holder.tujuanSurat.text = data.penerima.toString()
    }

    override fun getItemCount(): Int {
        return listSuratKeluar.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var judulSurat: TextView = itemView.findViewById(R.id.tvJudulSurat)
        var tujuanSurat: TextView = itemView.findViewById(R.id.tvDibuatUntuk)
        var keperluanSurat: TextView = itemView.findViewById(R.id.tvKeperluan)
        var tanggalSurat: TextView = itemView.findViewById(R.id.tvTanggal)
        var statusSurat: TextView = itemView.findViewById(R.id.tvStatus)
    }
}