package com.maluku.sma_rt.view.viewInterface

import com.maluku.sma_rt.model.informasi.GetAllInformasiItem
import com.maluku.sma_rt.model.informasi.GetInformasiById

interface InformasiInterface {
    fun onCreateInformasiSuccess(message: String)
    fun onCreateInformasiFailed(message: String)
    fun onGetAllInformasiSuccess(result: List<GetAllInformasiItem>)
    fun onGetAllInformasiFailed(message: String)
    fun onGetInformasiSuccess(result: List<GetInformasiById>)
    fun onGetInformasiFailed(message: String)
    fun showDataInfoTerkini(info: List<GetAllInformasiItem>)
    fun updateDataInfoTerkini(info: List<GetAllInformasiItem>)
    fun showDataKegiatan(kegiatan: List<GetAllInformasiItem>)
    fun updateDataKegiatan(kegiatan: List<GetAllInformasiItem>)
}