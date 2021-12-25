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
import com.maluku.sma_rt.model.informasi.GetAllInformasiItem
import com.maluku.sma_rt.model.keluarga.GetAllKeluargaItem
import java.io.File

class RecyclerViewToko(
    private val listToko: ArrayList<GetAllKeluargaItem>
): RecyclerView.Adapter<RecyclerViewToko.MyViewHolder>() {

    fun setData(data : List<GetAllKeluargaItem>){
        listToko.clear()
        listToko.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.toko_recycle_row_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listToko[position]
        holder.namaToko.text = data.namaToko
        // Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference.child("toko/${data.gambar}")
        Log.d(ContentValues.TAG,"Adapter get ref image: $storageRef")
        val localFile = File.createTempFile("tempFile","jpg")
        storageRef.getFile(localFile).addOnSuccessListener {
            Glide.with(holder.itemView)
                .load(localFile.path)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                .into(holder.gambarToko)
        }.addOnFailureListener {

        }

    }

    override fun getItemCount(): Int {
        return listToko.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var namaToko: TextView = itemView.findViewById(R.id.tv_namatoko)
        var gambarToko: ImageView = itemView.findViewById(R.id.iv_gambartoko)
    }
}