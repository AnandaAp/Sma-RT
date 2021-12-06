package com.maluku.sma_rt.presenter

import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.maluku.sma_rt.view.viewInterface.WargaHomeDashboardInterface

class WargaHomePresenter(private val view: WargaHomeDashboardInterface) {
    val TAG = "GET DATA"
    var list = ArrayList<String>()

    fun retrievePictureForGalleryAndKegiatanWarga(){
        val storageRef = Firebase.storage.reference.child("/images")
        storageRef.listAll()
            .addOnSuccessListener { it ->
                Log.i(TAG, "retrieveInfo: ${it.items[0].path}")
                it.items.forEach {
                    list.add(it.path)
                }
                view.onRetrieveGalleryWargaSuccess(list)
            }.addOnFailureListener {
                Log.i(TAG, "failed to retrieve data with log:\n${it.message}")
                view.onRetrieveGalleryWargaFailed(it.message.toString())
            }
    }
}