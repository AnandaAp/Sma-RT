package com.maluku.sma_rt.view.viewInterface

import com.maluku.sma_rt.model.dompetrt.GetDompetById

interface DompetRTInterface {
    fun onGetAllDataSuccess(result: GetDompetById?)
    fun onGetAllDataFailure(message: String)
    fun onWithdrawSuccess(message: String)
    fun onWithdrawFailure(message: String)
}