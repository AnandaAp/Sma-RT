package com.maluku.sma_rt.view.viewInterface

import com.maluku.sma_rt.model.informasi.GetAllInformasiItem

interface CreateInformasiInterface {
    fun onCreateInformasiSuccess(message: String)
    fun onCreateInformasiFailed(message: String)
}