package com.maluku.sma_rt.view.viewInterface

import com.maluku.sma_rt.model.warga.GetAllWargaItem

interface ListWargaViewInterface {
    fun resultListWargaSuccess(result: List<GetAllWargaItem>)
    fun resultListWargaFailed(t: Throwable)
}