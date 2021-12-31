package com.maluku.sma_rt.view.pengurus.adapter

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.maluku.sma_rt.R
import com.maluku.sma_rt.model.informasi.GetAllInformasiItem
import com.maluku.sma_rt.view.pengurus.bottomnavigation.HomeFragmentDirections
import java.io.File

class InfoAdapter(val listInfoTerkini: ArrayList<GetAllInformasiItem>): RecyclerView.Adapter<InfoAdapter.ViewHolder>() {
    fun setData(data : List<GetAllInformasiItem>){
        listInfoTerkini.clear()
        listInfoTerkini.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.info_terkini_rt,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listInfoTerkini[position]
        // Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference.child("images/${data.gambar}")
        Log.d(ContentValues.TAG,"Adapter get ref image: $storageRef")
        val localFile = File.createTempFile("tempFile","jpg")
        storageRef.getFile(localFile).addOnSuccessListener {
            Glide.with(holder.itemView)
                .load(localFile.path)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                .into(holder.infoTerkini)
        }.addOnFailureListener {

        }
        // List Info Terkini
        holder.cardInfo.setOnClickListener { view ->
            val direction = HomeFragmentDirections.actionNavigationHomeToDetailInformasiMasukFragment(
                data.judul.toString(),data.createdAt.toString(),data.detail.toString(),data.lokasi.toString(),data.gambar.toString()
            )
            view.findNavController().navigate(direction)
        }
    }

    override fun getItemCount(): Int {
        return listInfoTerkini.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var infoTerkini: ImageView = itemView.findViewById(R.id.ivInfoTerkiniRT)
        var cardInfo: CardView = itemView.findViewById(R.id.cardInfoTerkini)
    }

}