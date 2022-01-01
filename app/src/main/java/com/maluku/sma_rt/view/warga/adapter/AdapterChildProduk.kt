package com.maluku.sma_rt.view.warga.adapter

import android.content.ContentValues
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
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
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.order.ItemOrderItem
import com.maluku.sma_rt.view.warga.RiwayatPesananUserDirections
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList
import org.json.JSONArray
import org.json.JSONObject
import java.io.File


class AdapterChildProduk(private val orderItem: ArrayList<ItemOrderItem>, private val token: String) :
    RecyclerView.Adapter<AdapterChildProduk.MyViewHolder>() {

    fun setData(data : ArrayList<ItemOrderItem>){
        orderItem.clear()
        orderItem.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =  LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_child_produk,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return orderItem.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = orderItem[position]
//        holder.imageProdukChild.setImageResource(data.idProduk)
        holder.namaProdukChild.text = data.idProduk
        holder.jumlahProdukChild.text = "x${data.jumlah.toString()}"
        holder.hargaProdukChild.text = toRupiah(data.hargaTotal.toString().toDouble())

        holder.itemView.setOnClickListener { view ->
            val direction = RiwayatPesananUserDirections
                .actionRiwayatPesananUserToDetailPesananUser()
            view.findNavController().navigate(direction)
        }

        // buat dapetin nama produk
        AndroidNetworking.get("http://smart.aliven.my.id:2001/produk/{idProduk}")
            .addPathParameter("idProduk", data.idProduk)
            .addHeaders("Authorization", "Bearer ${token}")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    val data: JSONObject = response.getJSONObject("get_produk_by_id")
                    holder.namaProdukChild.text = data.getString("nama")
                    // Firebase Storage
                    val storageRef = FirebaseStorage.getInstance().reference.child("produk/${data.getString("gambar")}")
                    Log.d(ContentValues.TAG,"Adapter get ref image: $storageRef")
                    val localFile = File.createTempFile("tempFile","jpg")
                    storageRef.getFile(localFile).addOnSuccessListener {
                        Glide.with(holder.itemView)
                            .load(localFile.path)
                            .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                            .into(holder.imageProdukChild)
                    }.addOnFailureListener {

                    }
                }
                override fun onError(error: ANError?) {
                    holder.namaProdukChild.text = error!!.message.toString()
                }
            })
    }

    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var imageProdukChild: ImageView = itemView.findViewById(R.id.imageprodukchild)
        var namaProdukChild: TextView = itemView.findViewById(R.id.namaprodukchild)
        var jumlahProdukChild: TextView = itemView.findViewById(R.id.jumlahprodukchild)
        var hargaProdukChild: TextView = itemView.findViewById(R.id.hargaprodukchild)
    }

    private fun toRupiah(number: Double): String{
        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        return numberFormat.format(number).toString()
    }
}