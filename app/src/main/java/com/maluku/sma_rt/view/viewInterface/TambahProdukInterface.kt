package com.maluku.sma_rt.view.viewInterface

interface TambahProdukInterface {
    fun addProduct(
        nama: String,
        detail: String,
        gambar: String,
        harga: String
    )
    fun onCreateSuccess(message: String)
    fun onCreateFailed(message: String)
}