package com.maluku.sma_rt.view.testing

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.maluku.sma_rt.databinding.FragmentWargaHomeTestingBinding
import com.maluku.sma_rt.presenter.WargaHomePresenter
import com.maluku.sma_rt.view.viewInterface.WargaHomeDashboardInterface
import java.io.File

@GlideModule
class WargaHomeTesting : Fragment(), WargaHomeDashboardInterface {
    val TAG = "TEST STORAGE"
    private lateinit var binding: FragmentWargaHomeTestingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWargaHomeTestingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        WargaHomePresenter(this).retrievePictureForGalleryAndKegiatanWarga()
    }

    override fun onRetrieveGalleryWargaSuccess(downloadUrl: ArrayList<String>) {
        val headUrl = "gs://sma-rt.appspot.com"
        Log.i(TAG, "onRetrieveGalleryWargaSuccess: ${headUrl+downloadUrl[0]}")
        val storageRef = FirebaseStorage.getInstance().reference.child(downloadUrl[0])
        val localFile = File.createTempFile("tempFile","jpg")
        storageRef.getFile(localFile).addOnSuccessListener {
            Glide
                .with(requireActivity())
                .load(localFile.path)
                .override(200,200)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                .into(binding.testImage)
        }
    }

    override fun onRetrieveGalleryWargaFailed(message: String) {

    }

    override fun onRetrieveKegiatanWargaSuccess() {

    }

    override fun onRetrieveKegiatanWargaFailed(message: String) {

    }
}