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
import com.maluku.sma_rt.model.informasi.GetAllInformasiItem
import com.maluku.sma_rt.view.warga.InformasiWargaDirections
import com.maluku.sma_rt.view.warga.RiwayatPesananUserDirections
import java.io.File

class RecyclerViewInformasiWarga(
    private val listInformasi: ArrayList<GetAllInformasiItem>
): RecyclerView.Adapter<RecyclerViewInformasiWarga.MyViewHolder>() {

    fun setData(data : List<GetAllInformasiItem>){
        listInformasi.clear()
        listInformasi.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.informasi_warga_recycler_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listInformasi[position]

        holder.judul.text = data.judul.toString()
        if(data.detail!!.length > 23) {
            holder.desc.text = data.detail.toString().take(23)+"..."
        } else {
            holder.desc.text = data.detail.toString()
        }
        val storageRef = FirebaseStorage.getInstance().reference.child("images/${data.gambar}")
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
            val direction = InformasiWargaDirections
                .actionInformasiWargaToDetailInformasiBerita()
            view.findNavController().navigate(direction)
        }

    }

    override fun getItemCount(): Int {
        return listInformasi.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var judul: TextView = itemView.findViewById(R.id.judul_informasi)
        var desc: TextView = itemView.findViewById(R.id.desc_informasi)
        var gambar: ImageView = itemView.findViewById(R.id.image_infomasi)
        var layout: ConstraintLayout = itemView.findViewById(R.id.layoutInformasi)
    }

}