package com.maluku.sma_rt.view.warga.adapter

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.maluku.sma_rt.R
import com.maluku.sma_rt.model.aduan.GetAllAduanItem
import com.maluku.sma_rt.model.informasi.GetAllInformasiItem
import com.maluku.sma_rt.view.warga.LaporanListDirections
import java.io.File

class RecyclerViewLaporanList(
    private val listLaporan: ArrayList<GetAllAduanItem>
): RecyclerView.Adapter<RecyclerViewLaporanList.MyViewHolder>() {

    fun setData(data : List<GetAllAduanItem>){
        listLaporan.clear()
        listLaporan.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_laporanlist, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listLaporan[position]

        holder.judul.text = data.judul
        holder.deskripsi.text = data.deskripsi

        // Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference.child("aduan/${data.gambar}")
        Log.d(ContentValues.TAG,"Adapter get ref image: $storageRef")
        val localFile = File.createTempFile("tempFile","jpg")
        storageRef.getFile(localFile).addOnSuccessListener {
            Glide.with(holder.itemView)
                .load(localFile.path)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                .into(holder.gambar)
        }.addOnFailureListener {

        }

        holder.itemView.setOnClickListener { view ->
            val direction = LaporanListDirections
                .actionLaporanListToDetailLaporanList()
            view.findNavController().navigate(direction)
        }


    }

    override fun getItemCount(): Int {
        return listLaporan.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var judul: TextView = itemView.findViewById(R.id.namaPelapor)
        var deskripsi: TextView = itemView.findViewById(R.id.isiLaporan)
        var gambar: ImageView = itemView.findViewById(R.id.profileLaporanSaya)
        var layout: ConstraintLayout = itemView.findViewById(R.id.layoutLaporan)
    }
}