package com.maluku.sma_rt.view.viewInterface

import com.maluku.sma_rt.model.informasi.GetAllInformasiItem

interface CekLoginSessionInterface {
    fun resultSuccess(result: Boolean)
    fun resultFailed(t: Throwable)
}