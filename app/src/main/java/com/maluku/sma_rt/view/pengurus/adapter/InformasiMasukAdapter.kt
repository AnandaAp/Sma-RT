package com.maluku.sma_rt.view.pengurus.adapter

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
import java.io.File

class InformasiMasukAdapter(val listInformasi: ArrayList<GetAllInformasiItem>): RecyclerView.Adapter<InformasiMasukAdapter.ViewHolder>() {
    fun setData(data : List<GetAllInformasiItem>){
        listInformasi.clear()
        listInformasi.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_informasi_masuk, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listInformasi[position]
        holder.judulInformasi.text = data.judul.toString()
        holder.kategoriInformasi.text = data.kategori.toString()
        holder.lokasiInformasi.text = data.lokasi.toString()
        holder.detailInformasi.text = data.detail.toString()

        // Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference.child("images/${data.gambar}")
        Log.d(ContentValues.TAG,"Adapter get ref image: $storageRef")
        val localFile = File.createTempFile("tempFile","jpg")
        storageRef.getFile(localFile).addOnSuccessListener {
            // Tampilkan gambar dengan Glide
            Glide.with(holder.itemView)
                .load(localFile.path)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                .into(holder.gambarInformasi)
        }.addOnFailureListener {

        }
    }

    override fun getItemCount(): Int {
        return listInformasi.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)  {
        var judulInformasi: TextView = itemView.findViewById(R.id.tvJudulInformasi)
        var kategoriInformasi: TextView = itemView.findViewById(R.id.tvKategoriInformasi)
        var lokasiInformasi: TextView = itemView.findViewById(R.id.tvLokasiInformasi)
        var detailInformasi: TextView = itemView.findViewById(R.id.tvDetailInformasi)
        var gambarInformasi: ImageView = itemView.findViewById(R.id.ivInformasi)
    }

}