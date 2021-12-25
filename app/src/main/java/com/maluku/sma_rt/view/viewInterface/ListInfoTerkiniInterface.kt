package com.maluku.sma_rt.view.viewInterface

import com.maluku.sma_rt.model.informasi.GetAllInformasiItem

interface ListInfoTerkiniInterface {
    fun onGetInfoTerkiniSuccess(result: List<GetAllInformasiItem>)
    fun onGetInfoTerkiniFailed(message: String)
}