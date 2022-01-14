package com.maluku.sma_rt.view.warga.adapter

import android.content.ContentValues
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
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
import com.maluku.sma_rt.model.informasi.GetAllInformasiItem
import com.maluku.sma_rt.model.order.CreateOrderBody
import com.maluku.sma_rt.view.warga.LaporanPageDirections
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*
import kotlin.collections.ArrayList

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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listLaporan[position]

        if(data.judul!!.length > 20) {
            holder.judul.text = data.judul.toString().take(20) + "..."
        } else {
            holder.judul.text = data.judul.toString()
        }

        holder.status.text = formatTanggal(data.createdAt.toString().take(19))

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
            val dialog = BottomSheetDialog(view.context, R.style.AppBottomSheetDialogTheme)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.bottomsheet_listlaporan)

            val judul = dialog.findViewById<TextView>(R.id.judulLaporanlist)
            val gambar = dialog.findViewById<ImageView>(R.id.gambarLaporanlist)
            val deskripsi = dialog.findViewById<TextView>(R.id.deskripsiLaporanlist)

            judul!!.text = data.judul.toString()
            deskripsi!!.text = data.deskripsi.toString()
            val storageRef = FirebaseStorage.getInstance().reference.child("aduan/${data.gambar}")
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
    }

    override fun getItemCount(): Int {
        return listLaporan.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var judul: TextView = itemView.findViewById(R.id.judulLaporan)
        var deskripsi: TextView = itemView.findViewById(R.id.isiLaporan)
        var status: TextView = itemView.findViewById(R.id.jamLaporan)
        var gambar: ImageView = itemView.findViewById(R.id.buktiLaporan)
        var layoutlistlaporan: ConstraintLayout = itemView.findViewById(R.id.layout_listlaporan)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatTanggal(tanggal: String): String {
        val firstApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val date = LocalDate.parse(tanggal , firstApiFormat)

        return date.dayOfMonth.toString()+" "+date.month.getDisplayName(TextStyle.SHORT, Locale("id", "ID"))

    }
}