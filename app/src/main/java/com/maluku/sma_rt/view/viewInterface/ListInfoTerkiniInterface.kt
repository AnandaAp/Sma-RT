package com.maluku.sma_rt.view.viewInterface

import com.maluku.sma_rt.model.informasi.GetAllInformasiItem

interface ListInfoTerkiniInterface {
    fun showDataInfoTerkini(info: List<GetAllInformasiItem>)
    fun updateDataInfoTerkini(info: List<GetAllInformasiItem>)
}