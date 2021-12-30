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
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
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
        holder.judul.text = data.judul.toString()
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

        holder.layoutKegiatan.setOnClickListener { view ->
            val dialog = BottomSheetDialog(view.context, R.style.AppBottomSheetDialogTheme)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.bottomsheet_detailinformasi)

            val judul = dialog.findViewById<TextView>(R.id.namainformasi)
            val gambar = dialog.findViewById<ImageView>(R.id.gambarinformasi)
            val deskripsi = dialog.findViewById<TextView>(R.id.deskripsiinformasi)

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

            val btnClose = dialog.findViewById<ImageButton>(R.id.btn_close)

            btnClose?.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
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
        var judul: TextView = itemView.findViewById(R.id.tvKegiatanWarga)
        var gambarKegiatan: ImageView = itemView.findViewById(R.id.ivKegiatan)
        var layoutKegiatan: ConstraintLayout = itemView.findViewById(R.id.layoutKegiatan)
    }

}