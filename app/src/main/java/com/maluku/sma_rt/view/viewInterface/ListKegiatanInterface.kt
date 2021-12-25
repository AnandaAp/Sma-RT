package com.maluku.sma_rt.view.viewInterface

import com.maluku.sma_rt.model.informasi.GetAllInformasiItem

interface ListKegiatanInterface {
    fun onGetKegiatanSuccess(result: List<GetAllInformasiItem>)
    fun onGetKegiatanFailed(message: String)
}