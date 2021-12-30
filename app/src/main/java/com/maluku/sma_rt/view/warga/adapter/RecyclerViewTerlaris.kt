package com.maluku.sma_rt.view.warga.adapter

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.maluku.sma_rt.R
import com.maluku.sma_rt.model.keluarga.GetAllKeluargaItem
import com.maluku.sma_rt.model.produk.GetAllProdukItem
import java.io.File
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class RecyclerViewTerlaris(
    private val listProduk: ArrayList<GetAllProdukItem>
): RecyclerView.Adapter<RecyclerViewTerlaris.MyViewHolder>() {

    fun setData(data : List<GetAllProdukItem>){
        listProduk.clear()
        listProduk.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.terlaris_recycler_vertical_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listProduk[position]
        holder.namaBarang.text = data.nama
        holder.hargaBarang.text = toRupiah(data.harga.toString().toDouble())
        if(data.detail!!.length > 22) {
            holder.namaToko.text = data.detail.toString().take(22) + "..."
        } else {
            holder.namaToko.text = data.detail.toString()
        }

        // Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference.child("produk/${data.gambar}")
        Log.d(ContentValues.TAG,"Adapter get ref image: $storageRef")
        val localFile = File.createTempFile("tempFile","jpg")
        storageRef.getFile(localFile).addOnSuccessListener {
            Glide.with(holder.itemView)
                .load(localFile.path)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                .into(holder.gambarBarang)
        }.addOnFailureListener {

        }

    }

    override fun getItemCount(): Int {
        return listProduk.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var namaBarang: TextView = itemView.findViewById(R.id.tv_namabarang)
        var hargaBarang: TextView = itemView.findViewById(R.id.tv_hargabarang)
        var namaToko: TextView = itemView.findViewById(R.id.tv_namatoko)
        var gambarBarang: ImageView = itemView.findViewById(R.id.imageBarang)
    }

    private fun toRupiah(number: Double): String{
        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        return numberFormat.format(number).toString()
    }
}