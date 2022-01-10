package com.maluku.sma_rt.view.warga.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.maluku.sma_rt.R
import com.maluku.sma_rt.model.order.GetAllOrderItem
import com.maluku.sma_rt.model.order.ItemOrderItem
import org.json.JSONObject
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class AdapterParentListPesananMasuk(
    val listItem: ArrayList<GetAllOrderItem>,
    val token: String,
    val listener: OnAdapterListener
): RecyclerView.Adapter<AdapterParentListPesananMasuk.ViewHolder>() {
    fun setData(data: List<GetAllOrderItem>) {
        listItem.clear()
        listItem.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_parent_listpesananmasuk, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listItem[position]
        holder.total.text = toRupiah(data.hargaTotal.toString().toDouble())

        // buat dapetin nama pembeli
        AndroidNetworking.get("http://smart.aliven.my.id:2001/warga/detail/{idWarga}")
            .addPathParameter("idWarga", data.idWarga)
            .addHeaders("Authorization", "Bearer ${token}")
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    val data: JSONObject = response.getJSONObject("get_warga_by_id")
                    holder.namaPembeli.text = data.getString("nama")

                }
                override fun onError(error: ANError?) {
                    holder.namaPembeli.text = error!!.message.toString()
                }
            })

        holder.recyclerViewListPesanan.apply {
            val result: ArrayList<ItemOrderItem> = data.itemOrder as ArrayList<ItemOrderItem>
            layoutManager = LinearLayoutManager(holder.recyclerViewListPesanan.context, LinearLayoutManager.VERTICAL, false)
            adapter = AdapterChildListPesanan(result, token)
        }
        holder.btnTerima.setOnClickListener {
            listener.onProses(data.id.toString())
        }
        holder.btnTolak.setOnClickListener {
            listener.onCancel(data.id.toString())
        }
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var total: TextView = itemView.findViewById(R.id.total_harga)
        var btnTerima: LinearLayout = itemView.findViewById(R.id.btn_terima)
        var btnTolak: LinearLayout = itemView.findViewById(R.id.btnTolak)
        var namaPembeli: TextView = itemView.findViewById(R.id.namaPembeli)
        var recyclerViewListPesanan: RecyclerView = itemView.findViewById(R.id.rv_childlistpesanan)
    }

    interface OnAdapterListener {
        fun onProses(orderId:String)
        fun onCancel(orderId:String)
    }

    private fun toRupiah(number: Double): String{
        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        return numberFormat.format(number).toString()
    }
}