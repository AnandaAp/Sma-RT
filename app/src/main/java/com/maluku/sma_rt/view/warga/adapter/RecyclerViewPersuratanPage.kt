package com.maluku.sma_rt.view.warga.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.model.aduan.GetAllAduanItem
import com.maluku.sma_rt.model.persuratan.GetAllPersuratanItem
import com.maluku.sma_rt.view.warga.LaporanPageDirections
import com.maluku.sma_rt.view.warga.PersuratanPage
import com.maluku.sma_rt.view.warga.PersuratanPageDirections
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*
import kotlin.collections.ArrayList

class RecyclerViewPersuratanPage(
    private val listSurat: ArrayList<GetAllPersuratanItem>
):
    RecyclerView.Adapter<RecyclerViewPersuratanPage.MyViewHolder>() {

    fun setData(data : List<GetAllPersuratanItem>){
        listSurat.clear()
        listSurat.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_persuratan_page, parent, false)
        return MyViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listSurat[position]

        if(data.penerima!!.length > 30) {
            holder.nama.text = data.penerima.toString().take(30) + "..."
        } else {
            holder.nama.text = data.penerima.toString()
        }

        if(data.judul!!.length > 50) {
            holder.judul.text = data.judul.toString().take(50) + "..."
        } else {
            holder.judul.text = data.judul.toString()
        }

        if(data.keperluan!!.length > 50) {
            holder.deskripsi.text = data.keperluan.toString().take(50) + "..."
        } else {
            holder.deskripsi.text = data.keperluan.toString()
        }

        holder.status.text = data.status.toString()
        holder.jam.text = formatTanggal(data.createdAt.toString().take(19))


        holder.itemView.setOnClickListener { view ->
            val direction = PersuratanPageDirections
                .actionPersuratanPageToPersuratanPageDetail(data.id.toString(),data.penerima.toString(),data.judul.toString(),data.tanggal.toString(),data.keperluan.toString())
            view.findNavController().navigate(direction)
        }

    }

    override fun getItemCount(): Int {
        return listSurat.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var nama: TextView = itemView.findViewById(R.id.namaPelapor)
        var judul: TextView = itemView.findViewById(R.id.judulLaporan)
        var deskripsi: TextView = itemView.findViewById(R.id.isiLaporan)
        var status: TextView = itemView.findViewById(R.id.statusLaporan)
        var jam: TextView = itemView.findViewById(R.id.jamLaporan)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatTanggal(tanggal: String): String {
        val firstApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val date = LocalDate.parse(tanggal , firstApiFormat)

        return date.dayOfMonth.toString()+" "+date.month.getDisplayName(TextStyle.SHORT, Locale("id", "ID"))
    }
}