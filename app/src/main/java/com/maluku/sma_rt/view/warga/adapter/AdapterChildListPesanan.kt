package com.maluku.sma_rt.view.warga.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.maluku.sma_rt.R
import com.maluku.sma_rt.model.keluarga.GetAllProdukKeluargaItem
import com.maluku.sma_rt.model.order.ItemOrderItem
import com.maluku.sma_rt.model.produk.GetProdukById
import com.maluku.sma_rt.presenter.ProdukPresenter
import com.maluku.sma_rt.view.viewInterface.ProdukInterface
import org.json.JSONObject
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class AdapterChildListPesanan (
    private val orderItem: ArrayList<ItemOrderItem>,
    private val token: String
) : RecyclerView.Adapter<AdapterChildListPesanan.ViewHolder>(), ProdukInterface {
    private var namaProduk = ""

    fun setData(data : ArrayList<ItemOrderItem>){
        orderItem.clear()
        orderItem.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_child_listpesanan,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return orderItem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = orderItem[position]
        val idProduk = data.idProduk.toString()
        holder.jumlahBarang.text = "${data.jumlah.toString()} x"
        holder.hargaBarang.text = toRupiah(data.hargaTotal.toString().toDouble())

        // buat dapetin nama produk
        AndroidNetworking.get("http://smart.aliven.my.id:2001/produk/{idProduk}")
            .addPathParameter("idProduk", data.idProduk)
            .addHeaders("Authorization", "Bearer ${token}")
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    val data: JSONObject = response.getJSONObject("get_produk_by_id")
                    holder.namaBarang.text = data.getString("nama")

                }
                override fun onError(error: ANError?) {
                    holder.namaBarang.text = error!!.message.toString()
                }
            })

        ProdukPresenter(this).getProdukById(token,idProduk)
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var jumlahBarang: TextView = itemView.findViewById(R.id.jumlahbarang)
        var namaBarang: TextView = itemView.findViewById(R.id.namabarang)
        var hargaBarang: TextView = itemView.findViewById(R.id.hargabarang)
    }

    private fun toRupiah(number: Double): String{
        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        return numberFormat.format(number).toString()
    }

    override fun onGetAllDataSuccess(data: List<GetAllProdukKeluargaItem?>?) {
        TODO("Not yet implemented")
    }

    override fun onGetAllDataFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetDataSuccess(data: GetProdukById?) {
        namaProduk = data?.nama.toString()
    }


    override fun onGetDataFailure(message: String) {
        namaProduk = ""
    }

    override fun onCreateSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onCreateFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onUpdateSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onUpdateFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteFailure(message: String) {
        TODO("Not yet implemented")
    }

}