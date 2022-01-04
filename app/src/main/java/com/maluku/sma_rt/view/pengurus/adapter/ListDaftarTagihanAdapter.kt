package com.maluku.sma_rt.view.pengurus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.model.tagihan.GetAllTagihanItem

class ListDaftarTagihanAdapter (val listTagihan: ArrayList<GetAllTagihanItem>): RecyclerView.Adapter<ListDaftarTagihanAdapter.ViewHolder>() {
    fun setData(data : List<GetAllTagihanItem>){
        listTagihan.clear()
        listTagihan.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_daftar_iuran, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listTagihan[position]
        if (data.terbayar.toString() == "false"){
            holder.daftarIuranNama.text = data.namaKeluarga.toString()
            holder.daftarIuranJudul.text = data.nama.toString()
            holder.daftarIuranTotal.text = data.jumlah.toString()
        }
    }

    override fun getItemCount(): Int {
        return listTagihan.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var daftarIuranJudul: TextView = itemView.findViewById(R.id.daftarIuranJudulTagihan)
        var daftarIuranTotal: TextView = itemView.findViewById(R.id.daftarIuranTotalTagihan)
        var daftarIuranNama: TextView = itemView.findViewById(R.id.daftarIuranNamaTertagih)
    }
}