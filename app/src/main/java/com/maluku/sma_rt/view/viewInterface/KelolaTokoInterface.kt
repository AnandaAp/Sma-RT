package com.maluku.sma_rt.view.viewInterface

import com.maluku.sma_rt.model.keluarga.GetKeluargaSaya

interface KelolaTokoInterface {
    fun onUpdateSuccess(message: String)
    fun onUpdateFailure(message: String)
    fun onGetDataSuccess(list: GetKeluargaSaya?)
    fun onGetDataFailed(message: String)
}