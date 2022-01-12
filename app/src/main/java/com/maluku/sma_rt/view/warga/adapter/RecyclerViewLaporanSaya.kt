package com.maluku.sma_rt.view.warga.adapter

import android.content.ContentValues
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.storage.FirebaseStorage
import com.maluku.sma_rt.R
import com.maluku.sma_rt.model.aduan.GetAllAduanItem
import com.maluku.sma_rt.view.warga.InformasiWargaDirections
import com.maluku.sma_rt.view.warga.LaporanPageDirections
import java.io.File

class RecyclerViewLaporanSaya(
    private val listLaporan: ArrayList<GetAllAduanItem>
):
    RecyclerView.Adapter<RecyclerViewLaporanSaya.MyViewHolder>() {

    fun setData(data : List<GetAllAduanItem>){
        listLaporan.clear()
        listLaporan.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_laporansaya, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listLaporan[position]

        holder.judul.text = data.judul.toString()
        holder.status.text = data.status.toString()

        if(data.deskripsi!!.length > 30) {
            holder.deskripsi.text = data.deskripsi.toString().take(30) + "..."
        } else {
            holder.deskripsi.text = data.deskripsi.toString()
        }

        //Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference.child("aduan/${data.gambar}")
        Log.d(ContentValues.TAG,"Adapter get ref image   : $storageRef")
        val localFile = File.createTempFile("tempFile","jpg")
        storageRef.getFile(localFile).addOnSuccessListener {
            Glide.with(holder.itemView)
                .load(localFile.path)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                .into(holder.gambar)
        }.addOnFailureListener {

        }

        holder.itemView.setOnClickListener { view ->
            val direction = LaporanPageDirections
                .actionLaporanPageToDetailLaporanSaya(data.id.toString()
                )
            view.findNavController().navigate(direction)
        }

    }

    override fun getItemCount(): Int {
        return listLaporan.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var judul: TextView = itemView.findViewById(R.id.judulLaporan)
        var deskripsi: TextView = itemView.findViewById(R.id.isiLaporan)
        var status: TextView = itemView.findViewById(R.id.statusLaporan)
        var gambar: ImageView = itemView.findViewById(R.id.buktiLaporan)
    }
}