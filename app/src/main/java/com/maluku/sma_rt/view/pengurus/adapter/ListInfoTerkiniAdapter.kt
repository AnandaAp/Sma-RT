package com.maluku.sma_rt.view.pengurus.adapter

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
import com.maluku.sma_rt.view.pengurus.InformasiTerkiniRTFragmentDirections
import com.maluku.sma_rt.view.pengurus.bottomnavigation.HomeFragmentDirections
import java.io.File

class ListInfoTerkiniAdapter(val listInfoTerkini: ArrayList<GetAllInformasiItem>): RecyclerView.Adapter<ListInfoTerkiniAdapter.ViewHolder>() {
    fun setData(data : List<GetAllInformasiItem>){
        listInfoTerkini.clear()
        listInfoTerkini.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListInfoTerkiniAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_informasi_rt,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListInfoTerkiniAdapter.ViewHolder, position: Int) {
        val data = listInfoTerkini[position]
        holder.judulInfoTerkini.text = data.judul.toString()
        holder.detailInfoTerkini.text = data.detail.toString()
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
        holder.judulInfoTerkini.text = data.judul.toString()
        // List Info Terkini
        holder.cardInfo.setOnClickListener { view ->
            val direction = InformasiTerkiniRTFragmentDirections.actionInformasiTerkiniRTFragmentToDetailInformasiTerikiniRTFragment(
                data.judul.toString(),data.detail.toString(),data.gambar.toString()
            )
            view.findNavController().navigate(direction)
        }
    }

    override fun getItemCount(): Int {
        return listInfoTerkini.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var infoTerkini: ImageView = itemView.findViewById(R.id.ivListInformasi)
        var judulInfoTerkini: TextView = itemView.findViewById(R.id.tvJudulListInformasi)
        var detailInfoTerkini: TextView = itemView.findViewById(R.id.tvDescListInformasi)
        var cardInfo: CardView = itemView.findViewById(R.id.cardListInformasi)
    }

}