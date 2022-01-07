package com.maluku.sma_rt.view.viewInterface

import com.maluku.sma_rt.model.informasi.GetAllInformasiItem
import com.maluku.sma_rt.model.informasi.GetInformasiById

interface InformasiInterface {
    fun onCreateInformasiSuccess(message: String)
    fun onCreateInformasiFailure(message: String)
    fun onGetAllInformasiSuccess(result: List<GetAllInformasiItem>)
    fun onGetAllInformasiFailure(message: String)
    fun onGetInformasiSuccess(result: GetInformasiById?)
    fun onGetInformasiFailure(message: String)
    fun onGetInfoTerkiniSuccess(result: List<GetAllInformasiItem>)
    fun onGetInfoTerkiniFailure(message: String)
    fun onGetKegiatanSuccess(result: List<GetAllInformasiItem>)
    fun onGetKegiatanFailure(message: String)
    fun onUpdateInformasiSuccess(message: String)
    fun onUpdateInformasiFailure(message: String)
    fun onDeleteInformasiSuccess(message: String)
    fun onDeleteInformasiFailure(message: String)
}