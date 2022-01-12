package com.maluku.sma_rt.view.warga.adapter

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.maluku.sma_rt.R
import com.maluku.sma_rt.model.keluarga.GetAllKeluargaItem
import com.maluku.sma_rt.model.keluarga.GetAllProdukKeluargaItem
import com.maluku.sma_rt.model.keluarga.GetKeluargaById
import com.maluku.sma_rt.model.order.GetAllOrderItem
import com.maluku.sma_rt.model.order.ItemOrderItem
import com.maluku.sma_rt.model.order.ItemOrderItemById
import com.maluku.sma_rt.model.produk.GetProdukById
import com.maluku.sma_rt.view.warga.ProdukPageDirections
import com.maluku.sma_rt.view.warga.RiwayatPesananUser
import com.maluku.sma_rt.view.warga.RiwayatPesananUserDirections
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class RecyclerViewDetailPesanan(
    private val listOrder: ArrayList<ItemOrderItemById>, private val token: String
) : RecyclerView.Adapter<RecyclerViewDetailPesanan.MyViewHolder>() {

    fun setData(data : ArrayList<ItemOrderItemById>){
        listOrder.clear()
        listOrder.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_detailpesananuser,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listOrder[position]

        holder.itemView.setOnClickListener { view ->
//            val direction = RiwayatPesananUserDirections
//                .actionRiwayatPesananUserToDetailPesananUser()
//            view.findNavController().navigate(direction)
        }

        holder.jumlahProduk.text = "x${data.jumlah.toString()}"
        holder.hargaProduk.text = toRupiah(data.hargaTotal.toString().toDouble())

        // buat dapetin nama produk
        AndroidNetworking.get("http://smart.aliven.my.id:2001/produk/{idProduk}")
            .addPathParameter("idProduk", data.idProduk)
            .addHeaders("Authorization", "Bearer ${token}")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    val data: JSONObject = response.getJSONObject("get_produk_by_id")
                    holder.namaProduk.text = data.getString("nama")
                    // Firebase Storage
                    val storageRef = FirebaseStorage.getInstance().reference.child("produk/${data.getString("gambar")}")
                    Log.d(ContentValues.TAG,"Adapter get ref image: $storageRef")
                    val localFile = File.createTempFile("tempFile","jpg")
                    storageRef.getFile(localFile).addOnSuccessListener {
                        Glide.with(holder.itemView)
                            .load(localFile.path)
                            .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                            .into(holder.gambarProduk)
                    }.addOnFailureListener {

                    }
                }
                override fun onError(error: ANError?) {
                    holder.namaProduk.text = error!!.message.toString()
                }
            })

    }

    override fun getItemCount(): Int {
       return listOrder.size
    }

    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var namaProduk: TextView = itemView.findViewById(R.id.produkpesananuser)
        var jumlahProduk: TextView = itemView.findViewById(R.id.jumlahpesananuser)
        var hargaProduk: TextView = itemView.findViewById(R.id.hargapesananuser)
        var gambarProduk: ImageView = itemView.findViewById(R.id.image_detailpesananuser)
    }

    private fun toRupiah(number: Double): String{
        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        return numberFormat.format(number).toString()
    }
}


