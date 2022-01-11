package com.maluku.sma_rt.view.pengurus.adapter

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
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.storage.FirebaseStorage
import com.maluku.sma_rt.R
import com.maluku.sma_rt.model.informasi.GetAllInformasiItem
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

        holder.judulInfoTerkini.text = data.judul.toString()
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
            val dialog = BottomSheetDialog(view.context, R.style.AppBottomSheetDialogTheme)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.bottomsheet_detail_informasi_rt)

            val judul = dialog.findViewById<TextView>(R.id.tvJudulDetailLaporan)
            val gambar = dialog.findViewById<ImageView>(R.id.ivDetailLaporan)
            val deskripsi = dialog.findViewById<TextView>(R.id.tvKetDetailLaporan)

            judul!!.text = data.judul.toString()
            deskripsi!!.text = data.detail.toString()
            val storageRef = FirebaseStorage.getInstance().reference.child("images/${data.gambar}")
            Log.d(ContentValues.TAG,"Adapter get ref image: $storageRef")
            val localFile = File.createTempFile("tempFile","jpg")
            storageRef.getFile(localFile).addOnSuccessListener {
                Glide.with(holder.itemView)
                    .load(localFile.path)
                    .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                    .into(gambar!!)
            }.addOnFailureListener {
            }

            val btnClose = dialog.findViewById<ImageButton>(R.id.btnCloseInfoRT)

            btnClose?.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }
    }

    override fun getItemCount(): Int {
        return listInfoTerkini.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var infoTerkini: ImageView = itemView.findViewById(R.id.ivInfoTerkiniRT)
        var judulInfoTerkini: TextView = itemView.findViewById(R.id.judulInfoTerkini)
        var cardInfo: CardView = itemView.findViewById(R.id.cardInfoTerkini)
    }

}