package com.maluku.sma_rt.view.warga.adapter

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.maluku.sma_rt.R
import com.maluku.sma_rt.model.informasi.GetAllInformasiItem
import java.io.File

class RecyclerViewKegiatanWarga(
    private val listKegiatan: ArrayList<GetAllInformasiItem>
): RecyclerView.Adapter<RecyclerViewKegiatanWarga.MyViewHolder>() {

    fun setData(data : List<GetAllInformasiItem>){
        listKegiatan.clear()
        listKegiatan.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.galeri_recycler_row_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listKegiatan[position]
//        Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference.child("images/${data.gambar}")
        Log.d(ContentValues.TAG,"Adapter get ref image: $storageRef")
        val localFile = File.createTempFile("tempFile","jpg")
        storageRef.getFile(localFile).addOnSuccessListener {
            Glide.with(holder.itemView)
                .load(localFile.path)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                .into(holder.gambarKegiatan)
        }.addOnFailureListener {

        }

//        Glide.with(holder.itemView)
//            .load("${data.gambar}")
//            .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
//            .into(holder.gambarKegiatan)
    }

    override fun getItemCount(): Int {
        return listKegiatan.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var gambarKegiatan: ImageView = itemView.findViewById(R.id.ivKegiatanWarga)
    }

}