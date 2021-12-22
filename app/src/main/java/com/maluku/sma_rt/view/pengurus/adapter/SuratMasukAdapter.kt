package com.maluku.sma_rt.view.pengurus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R

class SuratMasukAdapter: RecyclerView.Adapter<SuratMasukAdapter.ViewHolder>() {
    private var judulSuratMasuk = arrayOf(
        "Surat Keterangan SKCK",
        "Surat Keterangan Penghasilan"
    )

    private var tujuanSuratMasuk = arrayOf(
        "Bapak Irvan",
        "Bapak Joko"
    )

    private var tanggalSuratMasuk = arrayOf(
        "12 Agustus 2021",
        "20 November 2021"
    )

    private var keperluanSuratMasuk = arrayOf(
        "Membuat SKCK",
        "Beasiswa"
    )

    private var statusSuratMasuk = arrayOf(
        "Diajukan",
        "Diterima"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_surat_masuk,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.judulSurat.text = judulSuratMasuk[position]
        holder.keperluanSurat.text = keperluanSuratMasuk[position]
        holder.statusSurat.text = statusSuratMasuk[position]
        holder.tanggalSurat.text = tanggalSuratMasuk[position]
        holder.tujuanSurat.text = tujuanSuratMasuk[position]
    }

    override fun getItemCount(): Int {
        return judulSuratMasuk.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var judulSurat: TextView = itemView.findViewById(R.id.vwJudulMasuk)
        var tujuanSurat: TextView = itemView.findViewById(R.id.vwTujuanMasuk)
        var keperluanSurat: TextView = itemView.findViewById(R.id.vwKeperluanMasuk)
        var tanggalSurat: TextView = itemView.findViewById(R.id.vwTanggalMasuk)
        var statusSurat: TextView = itemView.findViewById(R.id.vwStatusMasuk)
    }
}