package com.maluku.sma_rt.view.viewInterface

interface WargaHomeDashboardInterface {
    fun onRetrieveGalleryWargaSuccess(downloadUrl: ArrayList<String>)
    fun onRetrieveGalleryWargaFailed(message: String)
    fun onRetrieveKegiatanWargaSuccess()
    fun onRetrieveKegiatanWargaFailed(message: String)
}