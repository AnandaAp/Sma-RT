package com.maluku.sma_rt.view.viewInterface

import com.maluku.sma_rt.model.informasi.GetAllInformasiItem

interface ListKegiatanInterface {
    fun showDataKegiatan(kegiatan: List<GetAllInformasiItem>)
    fun updateDataKegiatan(kegiatan: List<GetAllInformasiItem>)
}