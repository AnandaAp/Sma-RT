package com.maluku.sma_rt.view.pengurus.adapter

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.maluku.sma_rt.R
import java.io.File

class GaleriAdapter: RecyclerView.Adapter<GaleriAdapter.ViewHolder>() {
    // Membuat array untuk menampung gambar kegiatan
    private var arrGambar = arrayOf(
        "gotong_royong.jpg",
        "hut_ri.jpg",
        "kerja_bakti.jpg",
        "kerja_bakti_2.jpg",
        "kerja_bakti_3.jpg"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.galeri_kegiatan_warga_rt,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gambarKegiatan = arrGambar[position]
        // Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference.child("images/${gambarKegiatan}")
        Log.d(ContentValues.TAG,"Adapter get ref image: $storageRef")
        val localFile = File.createTempFile("tempFile","jpg")
        storageRef.getFile(localFile).addOnSuccessListener {
//            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
//            holder.gambarProduk.setImageBitmap(bitmap)
            // Tampilkan gambar dengan Glide
            Glide.with(holder.itemView)
                .load(localFile.path)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                .into(holder.galeriWarga)
        }.addOnFailureListener {

        }
    }

    override fun getItemCount(): Int {
        return arrGambar.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var galeriWarga: ImageView = itemView.findViewById(R.id.ivGaleriWargaRT)
    }
}