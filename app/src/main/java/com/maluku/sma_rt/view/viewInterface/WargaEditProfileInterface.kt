package com.maluku.sma_rt.view.viewInterface

import com.maluku.sma_rt.model.warga.GetMe

interface WargaEditProfileInterface {
    fun onUpdateSuccess(message: String)
    fun onUpdateFailure(message: String)
    fun onGetDataSuccess(list: GetMe?)
    fun onGetDataFailed(message: String)
}