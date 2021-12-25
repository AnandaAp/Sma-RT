package com.maluku.sma_rt.view.viewInterface

import com.maluku.sma_rt.model.keluarga.GetAllKeluargaItem
import com.maluku.sma_rt.model.produk.GetAllProdukItem

interface WargaJualBeliInterface {
    fun showDataToko(toko: List<GetAllKeluargaItem>)
    fun updateDataToko(toko: List<GetAllKeluargaItem>)
    fun showDataProduk(produk: List<GetAllProdukItem>)
    fun updateDataProduk(produk: List<GetAllProdukItem>)
}