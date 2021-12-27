package com.maluku.sma_rt.view.pengurus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.model.tagihan.GetAllTagihanItem
import com.maluku.sma_rt.view.pengurus.DaftarBuatIuranFragmentDirections

class ListTagihanAdapter(val listTagihan: ArrayList<GetAllTagihanItem>): RecyclerView.Adapter<ListTagihanAdapter.ViewHolder>() {
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
        // Edit Tagihan
        holder.editTagihan.setOnClickListener { view ->
            val direction = DaftarBuatIuranFragmentDirections.actionDaftarBuatIuranFragmentToDetailsBuatIuranFragment2(
                data.id.toString(),data.nama.toString(),data.jumlah.toString(),data.detail.toString()
            )
            view.findNavController().navigate(direction)
        }
    }

    override fun getItemCount(): Int {
        return listTagihan.size
    }



    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var namaTagihan: TextView = itemView.findViewById(R.id.tvNamaTagihan)
        var ketTagihan: TextView = itemView.findViewById(R.id.tvKetTagihan)
        var totalTagihan: TextView = itemView.findViewById(R.id.tvTotalTagihan)
        var editTagihan: CardView = itemView.findViewById(R.id.cardTagihan)
    }
}