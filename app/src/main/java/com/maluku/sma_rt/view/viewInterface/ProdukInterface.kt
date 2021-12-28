package com.maluku.sma_rt.view.viewInterface

import com.maluku.sma_rt.model.keluarga.GetAllProdukKeluargaItem
import com.maluku.sma_rt.model.produk.GetProdukById

interface ProdukInterface {
    fun onGetAllDataSuccess(data: List<GetAllProdukKeluargaItem?>?)
    fun onGetAllDataFailure(message: String)
    fun onGetDataSuccess(data: GetProdukById?)
    fun onGetDataFailure(message: String)
    fun onCreateSuccess(message: String)
    fun onCreateFailure(message: String)
    fun onUpdateSuccess(message: String)
    fun onUpdateFailure(message: String)
    fun onDeleteSuccess(message: String)
    fun onDeleteFailure(message: String)
}