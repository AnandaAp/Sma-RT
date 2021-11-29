package com.maluku.sma_rt.view.viewInterface

import com.maluku.sma_rt.model.warga.GetAllWargaItem

interface DaftarWargaViewInterface {
    fun showDataWarga(warga: List<GetAllWargaItem>)
    fun updateDataWarga(warga: List<GetAllWargaItem>)
    fun resultSuccess(result: List<GetAllWargaItem>)
    fun resultFailed(t: Throwable)
}