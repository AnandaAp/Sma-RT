package com.maluku.sma_rt.view.viewInterface

import com.maluku.sma_rt.model.keluarga.GetAllProdukKeluargaItem

interface ListProdukInterface {
    fun showDataProduk(produk: List<GetAllProdukKeluargaItem>)
    fun updateDataProduk(produk: List<GetAllProdukKeluargaItem>)
    fun resultSuccess(result: List<GetAllProdukKeluargaItem>)
    fun resultFailed(t: Throwable)
    fun onDeleteSuccess(message: String)
    fun onDeleteFailure(message: String)
}