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
import com.maluku.sma_rt.model.warga.GetAllWargaItem
import java.io.File

class WargaAdapter(val listWarga: ArrayList<GetAllWargaItem>): RecyclerView.Adapter<WargaAdapter.ViewHolder>()  {
    fun setData(data : List<GetAllWargaItem>){
        listWarga.clear()
        listWarga.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_warga, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listWarga[position]
        val nama = data.nama.toString()
        val email = data.email.toString()
        val lengthNama = 20
        val lengthEmail = 20
        if (nama.length > lengthNama)
            holder.nama.text = nama.substring(0, lengthNama - 3) + "..."
        else
            holder.nama.text = nama
        if (email.length > lengthEmail)
            holder.email.text = email.substring(0, lengthEmail - 3) + "..."
        else
            holder.email.text = email

        holder.noHp.text = data.noHp.toString()
        holder.gender.text = data.gender.toString().capitalize()
        // Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference.child("user/${data.gambar}")
        Log.d(ContentValues.TAG,"Adapter get ref image: $storageRef")
        val localFile = File.createTempFile("tempFile","jpg")
        storageRef.getFile(localFile).addOnSuccessListener {
            // Tampilkan gambar dengan Glide
            Glide.with(holder.itemView)
                .load(localFile.path)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(10)))
                .into(holder.gambar)
        }.addOnFailureListener {

        }
    }

    override fun getItemCount(): Int {
        return listWarga.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var nama: TextView = itemView.findViewById(R.id.tv_nama)
        var noHp: TextView = itemView.findViewById(R.id.tv_noHp)
        var email: TextView = itemView.findViewById(R.id.tv_email)
        var gender: TextView = itemView.findViewById(R.id.tv_gender)
        var gambar: ImageView = itemView.findViewById(R.id.ivProfilWarga)
    }
}