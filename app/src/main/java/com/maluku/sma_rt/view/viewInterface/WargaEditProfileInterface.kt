package com.maluku.sma_rt.view.viewInterface

import com.maluku.sma_rt.model.warga.GetMe

interface WargaEditProfileInterface {
    fun onUpdateSuccess(message: String)
    fun onUpdateFailure(message: String)
    fun onGetDataSuccess(result: GetMe?)
    fun onGetDataFailure(message: String)
}