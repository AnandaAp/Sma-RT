package com.maluku.sma_rt.view.warga.adapter

import android.content.ContentValues
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Build.ID
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.storage.FirebaseStorage
import com.maluku.sma_rt.R
import com.maluku.sma_rt.model.persuratan.GetAllPersuratanItem
import com.maluku.sma_rt.view.warga.InformasiWargaDirections
import com.maluku.sma_rt.view.warga.LaporanPageDirections
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*
import kotlin.collections.ArrayList

class RecyclerViewSuratMasuk(
    private val listSurat: ArrayList<GetAllPersuratanItem>
): RecyclerView.Adapter<RecyclerViewSuratMasuk.MyViewHolder>() {

    fun setData(data : List<GetAllPersuratanItem>){
        listSurat.clear()
        listSurat.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_surat_masuk, parent, false)
        return MyViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listSurat[position]

        holder.judul.text = data.judul.toString() + " - " + data.status.toString()
        holder.deskripsi.text = data.keperluan.toString()
        holder.jam.text = formatTanggal(data.updatedAt.toString().take(19))

        holder.itemView.setOnClickListener { view ->
            val dialog = BottomSheetDialog(view.context, R.style.AppBottomSheetDialogTheme)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.bottomsheet_suratmasuk)

            val judulSurat = dialog.findViewById<TextView>(R.id.tv_pengurusRT)
            val isiSurat = dialog.findViewById<TextInputEditText>(R.id.ed_suratmasuk)
            val waktuSurat = dialog.findViewById<TextView>(R.id.waktu)

            judulSurat!!.text = data.judul.toString() + " - " + data.status.toString()
            isiSurat!!.setText(data.link.toString())
            waktuSurat!!.text = formatJam(data.updatedAt.toString().take(19))

            val btnClose = dialog.findViewById<ImageButton>(R.id.btn_close)

            btnClose?.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }

    }

    override fun getItemCount(): Int {
        return listSurat.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var judul: TextView = itemView.findViewById(R.id.judulLaporan)
        var deskripsi: TextView = itemView.findViewById(R.id.isiLaporan)
        var jam: TextView = itemView.findViewById(R.id.jamLaporan)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatTanggal(tanggal: String): String {
        val firstApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val date = LocalDate.parse(tanggal , firstApiFormat)

        return date.dayOfMonth.toString()+" "+date.month.getDisplayName(TextStyle.SHORT, Locale("id", "ID"))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatJam(tanggal: String): String {
        val firstApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val date = LocalDateTime.parse(tanggal , firstApiFormat).atZone(ZoneId.of("Asia/Jakarta"))

        return date.hour.toString()+":"+date.minute.toString()+" WIB"
    }
}