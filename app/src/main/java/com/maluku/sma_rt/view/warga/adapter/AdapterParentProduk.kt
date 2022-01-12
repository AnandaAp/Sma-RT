package com.maluku.sma_rt.view.warga.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.maluku.sma_rt.R
import com.maluku.sma_rt.model.keluarga.GetAllKeluargaItem
import com.maluku.sma_rt.model.keluarga.GetAllProdukKeluargaItem
import com.maluku.sma_rt.model.keluarga.GetKeluargaById
import com.maluku.sma_rt.model.order.GetAllOrderItem
import com.maluku.sma_rt.model.order.ItemOrderItem
import com.maluku.sma_rt.model.produk.GetProdukById
import com.maluku.sma_rt.view.warga.ProdukPageDirections
import com.maluku.sma_rt.view.warga.RiwayatPesananUser
import com.maluku.sma_rt.view.warga.RiwayatPesananUserDirections
import org.json.JSONArray
import org.json.JSONObject
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class AdapterParentProduk(
    private val listOrder: ArrayList<GetAllOrderItem>, private val token: String
) : RecyclerView.Adapter<AdapterParentProduk.MyViewHolder>() {

    fun setData(data : ArrayList<GetAllOrderItem>){
        listOrder.clear()
        listOrder.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_parent_produk,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listOrder[position]
        holder.namaToko.text = data.idKeluargaPenjual.toString()
        holder.status.text = data.status.toString()
        holder.total.text = toRupiah(data.hargaTotal.toString().toDouble())

        holder.recyclerView.apply {
            val result: ArrayList<ItemOrderItem> = data.itemOrder as ArrayList<ItemOrderItem>
            layoutManager = LinearLayoutManager(holder.recyclerView.context, LinearLayoutManager.VERTICAL, false)
            adapter = AdapterChildProduk(result, token)
        }

        holder.itemView.setOnClickListener { view ->
            val direction = RiwayatPesananUserDirections
                .actionRiwayatPesananUserToDetailPesananUser(data.id.toString())
            view.findNavController().navigate(direction)
        }

        // buat dapetin nama toko & gambar
        AndroidNetworking.get("http://smart.aliven.my.id:2001/keluarga/{idKeluarga}")
            .addPathParameter("idKeluarga", data.idKeluargaPenjual)
            .addHeaders("Authorization", "Bearer ${token}")
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    val data: JSONObject = response.getJSONObject("get_keluarga_by_id")
                    holder.namaToko.text = data.getString("nama_toko")
                }
                override fun onError(error: ANError?) {
                    holder.namaToko.text = error!!.message.toString()
                }
            })

    }

    override fun getItemCount(): Int {
       return listOrder.size
    }

    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var recyclerView: RecyclerView = itemView.findViewById(R.id.rv_child)
        var namaToko: TextView = itemView.findViewById(R.id.namatoko)
        var status: TextView = itemView.findViewById(R.id.btn_pesananmenunggu)
        var total: TextView = itemView.findViewById(R.id.tv_totalhargapesanan)
    }

    private fun toRupiah(number: Double): String{
        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        return numberFormat.format(number).toString()
    }
}


