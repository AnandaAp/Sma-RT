package com.maluku.sma_rt.view.viewInterface

import com.maluku.sma_rt.model.keluarga.GetAllKeluargaItem
import com.maluku.sma_rt.model.keluarga.GetKeluargaById
import com.maluku.sma_rt.model.order.GetOrderById
import com.maluku.sma_rt.model.produk.GetAllProdukItem

interface WargaJualBeliInterface {
    fun onGetAllTokoSuccess(result: List<GetAllKeluargaItem>)
    fun onGetAllTokoFailure(message: String)
    fun onGetAllProdukSuccess(result: List<GetAllProdukItem>)
    fun onGetAllProdukFailure(message: String)
    fun onGetKeluargaByIDSuccess(result: GetKeluargaById)
    fun onGetKeluargaByIDFailure(message: String)
}