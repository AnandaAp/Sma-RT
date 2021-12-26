package com.maluku.sma_rt.view.viewInterface

import com.maluku.sma_rt.model.informasi.GetAllInformasiItem

interface ListAllInformationInterface {
    fun resultListAllInformationSuccess(result: List<GetAllInformasiItem>)
    fun resultListAllInformationFailed(message: String)
}