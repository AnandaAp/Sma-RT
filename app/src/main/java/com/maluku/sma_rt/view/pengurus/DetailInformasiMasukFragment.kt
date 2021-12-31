package com.maluku.sma_rt.view.pengurus

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.maluku.sma_rt.databinding.FragmentDetailInformasiMasukBinding
import java.io.File

class DetailInformasiMasukFragment : Fragment() {
    private lateinit var binding: FragmentDetailInformasiMasukBinding
    private var judul: String = ""
    private var lokasi: String = ""
    private var date: String = ""
    private var detail: String = ""
    private var gambar: String = ""

    val args: DetailInformasiMasukFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindData()
    }

    private fun bindData() {
        judul = args.judulInformasi
        lokasi = args.lokasiInformasi
        date = args.tanggalInformasi
        detail = args.detailInformasi
        gambar = args.gambarInformasi
        setDetailInformasi()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return bindingView()
    }

    private fun bindingView(): View? {
        binding = FragmentDetailInformasiMasukBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun setDetailInformasi(){
        binding.tvJudulInformasi.text = judul
        binding.tvLokasiInformasi.text = lokasi
        binding.tvTanggalInformasi.text = tglIndonesia(splitDate())
        binding.tvDetailInformasi.text = detail
        // Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference.child("images/${gambar}")
        Log.d(ContentValues.TAG,"Adapter get ref image: $storageRef")
        val localFile = File.createTempFile("tempFile","jpg")
        storageRef.getFile(localFile).addOnSuccessListener {
            // Tampilkan gambar dengan Glide
            Glide.with(this)
                .load(localFile.path)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                .into(binding.ivInformasi)
        }.addOnFailureListener {

        }
    }

    private fun splitDate(): String {
        val delim = "T"
        val tanggal = date.split(delim)
        return tanggal[0]
    }

    fun tglIndonesia(tgl: String): String {
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