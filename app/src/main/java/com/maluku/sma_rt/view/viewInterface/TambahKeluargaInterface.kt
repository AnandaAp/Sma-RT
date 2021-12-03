package com.maluku.sma_rt.view.viewInterface

interface TambahKeluargaInterface {
    fun addFamily(
        nama: String
    )
    fun onCreateSuccess(message: String)
    fun onCreateFailed(message: String)
}