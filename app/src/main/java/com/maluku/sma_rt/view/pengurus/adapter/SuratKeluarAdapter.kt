package com.maluku.sma_rt.view.pengurus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R

class SuratKeluarAdapter: RecyclerView.Adapter<SuratKeluarAdapter.ViewHolder>() {
    // Membuat array untuk menampung gambar kegiatan
    private var judulSuratKeluar = arrayOf(
        "Surat Keterangan SKCK",
        "Surat Keterangan Penghasilan"
    )

    private var tujuanSuratKeluar = arrayOf(
        "Bapak Irvan",
        "Bapak Joko"
    )

    private var tanggalSuratKeluar = arrayOf(
        "12 Agustus 2021",
        "20 November 2021"
    )

    private var keperluanSuratKeluar = arrayOf(
        "Membuat SKCK",
        "Beasiswa"
    )

    private var statusSuratKeluar = arrayOf(
        "Diajukan",
        "Diterima"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_surat_keluar,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.judulSurat.text = judulSuratKeluar[position]
        holder.keperluanSurat.text = keperluanSuratKeluar[position]
        holder.statusSurat.text = statusSuratKeluar[position]
        holder.tanggalSurat.text = tanggalSuratKeluar[position]
        holder.tujuanSurat.text = tujuanSuratKeluar[position]
    }

    override fun getItemCount(): Int {
        return judulSuratKeluar.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var judulSurat: TextView = itemView.findViewById(R.id.tvJudulSurat)
        var tujuanSurat: TextView = itemView.findViewById(R.id.tvDibuatUntuk)
        var keperluanSurat: TextView = itemView.findViewById(R.id.tvKeperluan)
        var tanggalSurat: TextView = itemView.findViewById(R.id.tvTanggal)
        var statusSurat: TextView = itemView.findViewById(R.id.tvStatus)
    }
}