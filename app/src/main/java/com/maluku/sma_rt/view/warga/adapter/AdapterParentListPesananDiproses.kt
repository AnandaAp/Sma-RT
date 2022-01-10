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

class AdapterParentListPesananDiproses(
    val listPesananDiproses: ArrayList<GetAllOrderItem>,
    val token: String,
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
        holder.total.text = toRupiah(data.hargaTotal.toString().toDouble())
//        holder.idOrderProses.text = data.id.toString()

        // buat dapetin nama pembeli
        AndroidNetworking.get("http://smart.aliven.my.id:2001/warga/detail/{idWarga}")
            .addPathParameter("idWarga", data.idWarga)
            .addHeaders("Authorization", "Bearer ${token}")
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    val data: JSONObject = response.getJSONObject("get_warga_by_id")
                    holder.idOrderProses.text = data.getString("nama")

                }
                override fun onError(error: ANError?) {
                    holder.idOrderProses.text = error!!.message.toString()
                }
            })

        holder.recyclerViewListPesanan.apply {
            val result: ArrayList<ItemOrderItem> = data.itemOrder as ArrayList<ItemOrderItem>
            layoutManager = LinearLayoutManager(holder.recyclerViewListPesanan.context, LinearLayoutManager.VERTICAL, false)
            adapter = AdapterChildListPesanan(result, token)
        }
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
        var idOrderProses: TextView = itemView.findViewById(R.id.tvIdOrderProses)
        var recyclerViewListPesanan: RecyclerView = itemView.findViewById(R.id.rv_childlistpesanan)
    }

    interface OnAdapterListener {
        fun onSelesai(orderId:String)
    }

    private fun toRupiah(number: Double): String{
        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        return numberFormat.format(number).toString()
    }
}