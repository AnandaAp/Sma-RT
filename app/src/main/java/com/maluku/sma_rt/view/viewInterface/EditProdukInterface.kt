package com.maluku.sma_rt.view.viewInterface

interface EditProdukInterface {
    fun updateProduct(
        nama: String,
        detail: String,
        gambar: String,
        harga: String
    )
    fun onUpdateSuccess(message: String)
    fun onUpdateFailure(message: String)
    fun onDeleteSuccess(message: String)
    fun onDeleteFailure(message: String)
}