package com.maluku.sma_rt.view.pengurus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.model.persuratan.GetAllPersuratanItem
import com.maluku.sma_rt.view.pengurus.SuratFragmentDirections

class SuratMasukAdapter(val listSuratMasuk: ArrayList<GetAllPersuratanItem>): RecyclerView.Adapter<SuratMasukAdapter.ViewHolder>() {
    fun setData(data : List<GetAllPersuratanItem>){
        listSuratMasuk.clear()
        listSuratMasuk.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_surat_masuk,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listSuratMasuk[position]

        val judul = data.judul.toString()
        val keperluan = data.keperluan.toString()
        val lengthJudul = 30
        val lengthKeperluan = 30
        if (judul.length > lengthJudul)
            holder.judulSurat.text = judul.substring(0, lengthJudul - 3) + "..."
        else
            holder.judulSurat.text = judul
        if (keperluan.length > lengthKeperluan)
            holder.keperluanSurat.text = keperluan.substring(0, lengthKeperluan - 3) + "..."
        else
            holder.keperluanSurat.text = keperluan

        holder.statusSurat.text = "Diajukan"
        holder.tanggalSurat.text = data.tanggal.toString()
        holder.tujuanSurat.text = data.penerima.toString()
        holder.cardSurat.setOnClickListener { view ->
            val direction = SuratFragmentDirections.actionSuratFragmentToDetailSuratMasuk(
                data.id.toString()
            )
            view!!.findNavController()!!.navigate(direction)
        }
    }

    override fun getItemCount(): Int {
        return listSuratMasuk.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var judulSurat: TextView = itemView.findViewById(R.id.vwJudulMasuk)
        var tujuanSurat: TextView = itemView.findViewById(R.id.vwTujuanMasuk)
        var keperluanSurat: TextView = itemView.findViewById(R.id.vwKeperluanMasuk)
        var tanggalSurat: TextView = itemView.findViewById(R.id.vwTanggalMasuk)
        var statusSurat: TextView = itemView.findViewById(R.id.vwStatusMasuk)
        var cardSurat: CardView = itemView.findViewById(R.id.cardSurat)
    }

}