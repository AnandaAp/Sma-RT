package com.maluku.sma_rt.view.pengurus.adapter

import android.content.ContentValues
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
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
import com.maluku.sma_rt.model.aduan.GetAllAduanItem
import java.io.File

class LaporanDiterimaAdapter (
    val listLaporan: ArrayList<GetAllAduanItem>
): RecyclerView.Adapter<LaporanDiterimaAdapter.ViewHolder>() {
    fun setData(data : List<GetAllAduanItem>){
        listLaporan.clear()
        listLaporan.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_laporan, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listLaporan[position]
        holder.judulLaporan.text = data.judul.toString()
        holder.keteranganLaporan.text = data.deskripsi.toString()
        // Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference.child("aduan/${data.gambar}")
        Log.d(ContentValues.TAG,"Adapter get ref image: $storageRef")
        val localFile = File.createTempFile("tempFile","jpg")
        storageRef.getFile(localFile).addOnSuccessListener {
            // Tampilkan gambar dengan Glide
            Glide.with(holder.itemView)
                .load(localFile.path)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                .into(holder.gambar)
        }.addOnFailureListener {

        }

        // Item Laporan
        holder.cardLaporan.setOnClickListener { view ->
            val dialog = BottomSheetDialog(view.context, R.style.AppBottomSheetDialogTheme)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.bottomsheet_detail_laporan)

            val judulDetail = dialog.findViewById<TextView>(R.id.tvJudulDetailLaporan)
            val gambarDetail = dialog.findViewById<ImageView>(R.id.ivDetailLaporan)
            val deskripsiDetail = dialog.findViewById<TextView>(R.id.tvKetDetailLaporan)
            val btnTerimaAduan = dialog.findViewById<Button>(R.id.btnTerimaAduan)
            btnTerimaAduan!!.visibility = View.GONE

            judulDetail!!.text = data.judul.toString()
            deskripsiDetail!!.text = data.deskripsi.toString()
            val storageRef = FirebaseStorage.getInstance().reference.child("aduan/${data.gambar}")
            Log.d(ContentValues.TAG,"Adapter get ref image: $storageRef")
            val localFile = File.createTempFile("tempFile","jpg")
            storageRef.getFile(localFile).addOnSuccessListener {
                Glide.with(holder.itemView)
                    .load(localFile.path)
                    .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                    .into(gambarDetail!!)
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
        return listLaporan.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var judulLaporan: TextView = itemView.findViewById(R.id.tvJudulLaporan)
        var keteranganLaporan: TextView = itemView.findViewById(R.id.tvKetLaporan)
        var gambar: ImageView = itemView.findViewById(R.id.ivListLaporan)
        var cardLaporan: CardView = itemView.findViewById(R.id.cardLaporanPengurus)
    }

}