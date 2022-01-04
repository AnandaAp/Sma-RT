package com.maluku.sma_rt.view.pengurus

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentDetailGaleriKegiatanBinding
import java.io.File


class DetailGaleriKegiatanFragment : Fragment() {
    private lateinit var binding: FragmentDetailGaleriKegiatanBinding
    private var judul: String = ""
    private var detail: String = ""
    private var gambar: String = ""

    val args: DetailGaleriKegiatanFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindData()
        back()
    }

    private fun bindData() {
        judul = args.judulKegiatan
        detail = args.descKegiatan
        gambar = args.gambarKegiatan
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
        binding = FragmentDetailGaleriKegiatanBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun setDetailInformasi(){
        binding.judulDetailKegiatan.text = judul
        binding.descDetailKegiatan.text = detail
        // Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference.child("images/${gambar}")
        Log.d(ContentValues.TAG,"Adapter get ref image: $storageRef")
        val localFile = File.createTempFile("tempFile","jpg")
        storageRef.getFile(localFile).addOnSuccessListener {
            // Tampilkan gambar dengan Glide
            Glide.with(this)
                .load(localFile.path)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                .into(binding.ivDetailKegiatan)
        }.addOnFailureListener {

        }
    }

    private fun back(){
        binding.btnBack.setOnClickListener{
            findNavController().popBackStack()
        }
    }
}