package com.maluku.sma_rt.view.viewInterface

import com.maluku.sma_rt.model.keluarga.GetAllKeluargaWargaItem

interface ListKeluargaViewInterface {
    fun resultListKeluargaSuccess(result: List<GetAllKeluargaWargaItem>)
    fun resultListKeluargaFailed(t: Throwable)
}