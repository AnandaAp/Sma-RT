package com.maluku.sma_rt.view.viewInterface

import com.maluku.sma_rt.model.dompetkeluarga.GetAllDompetKeluargaItem
import com.maluku.sma_rt.model.dompetkeluarga.GetDompetKeluargaById

interface DompetKeluargaInterface {
    fun onTopupSuccess(message: String)
    fun onTopupFailure(message: String)
    fun onWithdrawSuccess(message: String)
    fun onWithdrawFailure(message: String)
    fun onGetAllDataSuccess(list: List<GetAllDompetKeluargaItem?>?)
    fun onGetAllDataFailed(message: String)
    fun onGetDataSuccess(list: GetDompetKeluargaById?)
    fun onGetDataFailed(message: String)
}