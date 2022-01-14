package com.maluku.sma_rt.view.warga.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
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
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class AdapterParentListPesananSelesai(val listPesananSelesai: ArrayList<GetAllOrderItem>,
val token: String
): RecyclerView.Adapter<AdapterParentListPesananSelesai.ViewHolder>() {
    fun setData(data: List<GetAllOrderItem>) {
        listPesananSelesai.clear()
        listPesananSelesai.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_parent_listpesananselesai, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listPesananSelesai[position]
        holder.total.text = toRupiah(data.hargaTotal.toString().toDouble())
        holder.waktu.text = formatJam(data.createdAt.toString().take(19))
//        holder.idOrderSelesai.text = data.id.toString()

        // buat dapetin nama pembeli
        AndroidNetworking.get("http://smart.aliven.my.id:2001/warga/detail/{idWarga}")
            .addPathParameter("idWarga", data.idWarga)
            .addHeaders("Authorization", "Bearer ${token}")
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    val data: JSONObject = response.getJSONObject("get_warga_by_id")
                    holder.idOrderSelesai.text = data.getString("nama")

                }
                override fun onError(error: ANError?) {
                    holder.idOrderSelesai.text = error!!.message.toString()
                }
            })

        holder.recyclerViewListPesanan.apply {
            val result: ArrayList<ItemOrderItem> = data.itemOrder as ArrayList<ItemOrderItem>
            layoutManager = LinearLayoutManager(holder.recyclerViewListPesanan.context, LinearLayoutManager.VERTICAL, false)
            adapter = AdapterChildListPesanan(result, token)
        }
    }

    override fun getItemCount(): Int {
        return listPesananSelesai.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var total: TextView = itemView.findViewById(R.id.total_harga)
        var idOrderSelesai: TextView = itemView.findViewById(R.id.tvNamaWarga)
        var waktu: TextView = itemView.findViewById(R.id.waktu_mubazir)
        var recyclerViewListPesanan: RecyclerView = itemView.findViewById(R.id.rv_childlistpesanan)
    }

    private fun toRupiah(number: Double): String{
        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        return numberFormat.format(number).toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatJam(tanggal: String): String {
        val firstApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val date = LocalDateTime.parse(tanggal , firstApiFormat).atZone(ZoneId.of("Asia/Jakarta"))

        return date.hour.toString()+":"+date.minute.toString()+" WIB"
    }
}