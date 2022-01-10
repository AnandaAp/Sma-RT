package com.maluku.sma_rt.view.warga

import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentDetailInformasiBeritaBinding
import com.maluku.sma_rt.databinding.FragmentDetailLaporanListBinding
import com.maluku.sma_rt.databinding.FragmentDetailPesananUserBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.view.activity.MainActivity
import java.io.File


class DetailInformasiBerita : Fragment() {
    val args: DetailInformasiBeritaArgs by navArgs()

    private lateinit var binding: FragmentDetailInformasiBeritaBinding

    private var judul: String = ""
    private var detail: String = ""
    private var gambar: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = bindingView()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindData()
        goBack()
    }

    private fun goBack(){
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailInformasiBerita_to_informasiWarga)
        }
    }

    private fun bindingView(): View {
        binding = FragmentDetailInformasiBeritaBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun bindData() {
        judul = args.informasiJudul
        detail = args.informasiDetail
        gambar = args.informasiGambar

        binding.judulInformasi.setText(judul)
        binding.detailInformasi.setText(detail)

        // Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference.child("images/${gambar}")
        Log.d(ContentValues.TAG,"Adapter get ref image: $storageRef")
        val localFile = File.createTempFile("tempFile","jpg")
        storageRef.getFile(localFile).addOnSuccessListener {
//            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
//            holder.gambarProduk.setImageBitmap(bitmap)
            // Tampilkan gambar dengan Glide
            Glide.with(this)
                .load(localFile.path)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                .into(binding.imageDetailinfo)
        }.addOnFailureListener {

        }

    }

}