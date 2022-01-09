package com.maluku.sma_rt.view.warga.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.model.order.GetAllOrderItem


class AdapterParentListPesananDiproses(
    val listPesananDiproses: ArrayList<GetAllOrderItem>,
    val listener: OnAdapterListener
): RecyclerView.Adapter<AdapterParentListPesananDiproses.ViewHolder>() {
    fun setData(data: List<GetAllOrderItem>) {
        listPesananDiproses.clear()
        listPesananDiproses.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_parent_listpesanandiproses, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listPesananDiproses[position]
        holder.total.text = data.hargaTotal.toString()
        holder.btnSelesai.setOnClickListener {
            listener.onSelesai(data.id.toString())
        }
    }

    override fun getItemCount(): Int {
        return listPesananDiproses.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var total: TextView = itemView.findViewById(R.id.total_harga)
        var btnSelesai: LinearLayout = itemView.findViewById(R.id.btn_selesai)
    }

    interface OnAdapterListener {
        fun onSelesai(orderId:String)
    }
}