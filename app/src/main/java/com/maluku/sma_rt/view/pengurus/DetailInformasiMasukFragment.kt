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
    private var tanggal: String = ""
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
        tanggal = args.tanggalInformasi
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
        binding.tvTanggalInformasi.text = tanggal
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

}