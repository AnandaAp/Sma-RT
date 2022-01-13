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
import com.maluku.sma_rt.view.pengurus.DaftarBuatIuranFragmentDirections
import com.maluku.sma_rt.view.pengurus.InformasiFragmentDirections
import com.maluku.sma_rt.view.pengurus.InformasiMasukFragmentDirections
import java.io.File

class InformasiMasukAdapter(val listInformasi: ArrayList<GetAllInformasiItem>): RecyclerView.Adapter<InformasiMasukAdapter.ViewHolder>() {
    fun setData(data : List<GetAllInformasiItem>){
        listInformasi.clear()
        listInformasi.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_informasi_masuk, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listInformasi[position]
        val judul = data.judul.toString()
        val lokasi = data.lokasi.toString()
        val lengthJudul = 17
        val lengthLokasi = 17
        if (judul.length > lengthJudul)
            holder.judulInformasi.text = judul.substring(0, lengthJudul - 3) + "..."
        else
            holder.judulInformasi.text = judul
        if (lokasi.length > lengthLokasi)
            holder.lokasiInformasi.text = lokasi.substring(0, lengthLokasi - 3) + "..."
        else
            holder.lokasiInformasi.text = lokasi

        holder.tanggalInformasi.text = tglIndonesia(splitDate(data.createdAt.toString()))

        // Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference.child("images/${data.gambar}")
        Log.d(ContentValues.TAG,"Adapter get ref image: $storageRef")
        val localFile = File.createTempFile("tempFile","jpg")
        storageRef.getFile(localFile).addOnSuccessListener {
            // Tampilkan gambar dengan Glide
            Glide.with(holder.itemView)
                .load(localFile.path)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                .into(holder.gambarInformasi)
        }.addOnFailureListener {

        }

        // List Informasi
        holder.cardInformasi.setOnClickListener { view ->
            val direction = InformasiFragmentDirections.actionInformasiFragmentToDetailInformasiMasukFragment(
                data.id.toString()
            )
            view!!.findNavController()!!.navigate(direction)
        }
    }


    override fun getItemCount(): Int {
        return listInformasi.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)  {
        var judulInformasi: TextView = itemView.findViewById(R.id.tvJudulInformasiMasuk)
        var lokasiInformasi: TextView = itemView.findViewById(R.id.tvLokasiInformasiMasuk)
        var tanggalInformasi: TextView = itemView.findViewById(R.id.tvTanggalInformasiMasuk)
        var gambarInformasi: ImageView = itemView.findViewById(R.id.ivInformasiMasuk)
        var cardInformasi: CardView = itemView.findViewById(R.id.cardInformasiMasuk)
    }

    private fun splitDate(date: String): String {
        val delim = "T"
        val tanggal = date.split(delim)
        return tanggal[0]
    }

    private fun tglIndonesia(tgl: String): String {
        val tgl = tgl.split("-")
        val bulan = when (tgl[1]) {
            "01" -> "Januari"
            "02" -> "Februari"
            "03" -> "Maret"
            "04" -> "April"
            "05" -> "Mei"
            "06" -> "Juni"
            "07" -> "Juli"
            "08" -> "Agustus"
            "09" -> "September"
            "10" -> "Oktober"
            "11" -> "November"
            "12" -> "Desember"
            else -> "Terjadi kesalahan"
        }
        return "${tgl[2]} $bulan ${tgl[0]}"
    }

}