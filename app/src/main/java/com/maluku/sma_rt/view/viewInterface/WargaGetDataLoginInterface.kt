package com.maluku.sma_rt.view.viewInterface

import com.maluku.sma_rt.model.warga.GetMe

interface WargaGetDataLoginInterface {
    fun onUpdateSuccess(message: String)
    fun onUpdateFailure(message: String)
    fun onGetDataSuccess(list: GetMe?)
    fun onGetDataFailed(message: String)
}