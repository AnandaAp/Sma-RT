package com.maluku.sma_rt.view.viewInterface

import com.maluku.sma_rt.model.pengurus.GetPengurusById

interface AdminRTProfileInterface {
    fun onUpdateSuccess(message: String)
    fun onUpdateFailure(message: String)
    fun onGetDataSuccess(result: GetPengurusById?)
    fun onGetDataFailed(message: String)
}