package com.maluku.sma_rt.view.viewInterface

import com.maluku.sma_rt.model.dompetkeluarga.GetAllDompetKeluargaItem
import com.maluku.sma_rt.model.dompetkeluarga.GetDompetKeluargaById

interface DompetKeluargaInterface {
    fun onTopupSuccess(message: String)
    fun onTopupFailure(message: String)
    fun onWithdrawSuccess(message: String)
    fun onWithdrawFailure(message: String)
    fun onGetAllDataSuccess(result: List<GetAllDompetKeluargaItem?>?)
    fun onGetAllDataFailed(message: String)
    fun onGetDataSuccess(result: GetDompetKeluargaById?)
    fun onGetDataFailure(message: String)
}